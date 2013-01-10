
;;; core.clj
;;;
;;; This file is part of Abbot.  It contains main and associated
;;; functions.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Mon Aug 06 17:39:32 CDT 2012
;;;
;;; Copyright © 2011-2013 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See COPYING for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See COPYING
;;; for more details.

(ns edu.unl.abbot.core
  (:import
    (java.io File)) 
  (:use edu.unl.abbot.stylesheets)
  (:use edu.unl.abbot.utils)
	(:use clojure.java.io)
	(:gen-class))

(defn input-files [input-dir]
  "Read input file and do some basic sanity checking."
  ; Apparently, the only truly reliable way to check that a text file
  ; is indeed an XML file is to parse it.  The XML declaration is
  ; optional, and different legal unicode encodings may or may not
  ; have a byte order mark.  The .xml extension is everywhere used
  ; in the specification, but nowhere mandated.
  ;
  ; So we check that the file is, in fact, a file, and demand an .xml
  ; extension.  Notification of more insidious file errors will have
  ; to be left to Saxon.
	(let [filters [#(.isFile %)
								 #(has-xml-extension? %)]]
    (filter (fn [x] (every? #(% x) filters)) (file-seq (File. input-dir)))))

(defn converter [output-dir stylesheet]
	"Returns a function that runs the conversion and writes out the file"
	; Written as a clojure to keep the main convert-files function
	; uncluttered.  
	(fn [x] (spit (str output-dir (.getName x)) (apply-master stylesheet x))))

(defn convert-files [{input-dir  :inputdir
                      output-dir :outputdir
											custom 		 :custom
											namespace  :namespace
                      single     :single
                      target		 :target
											}]
  "Apply the conversion stylesheet to the input files."
	(let [params (hash-map :n namespace :c custom :t target)
				stylesheet (conversion-stylesheet target params)
				converter (converter output-dir stylesheet)
        input (input-files input-dir)]
		(if single
			(doall (map converter input))
      (doall (pmap converter input)))))
