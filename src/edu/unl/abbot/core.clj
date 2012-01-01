
;;; core.clj
;;;
;;; This file is part of Abbot.  It contains main and associated
;;; functions.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Sun Jan 01 09:14:10 CST 2012
;;;
;;; Copyright © 2011 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
;;; for more details.

(ns edu.unl.abbot.core
  (:use edu.unl.abbot.stylesheets)
  (:use edu.unl.abbot.utils)
  (import
    (java.io File))
	(gen-class))
;  	:name edu.unl.abbot.Abbot
;  	:init init
;  	:state state
;		:main true
;  	:methods [
;			[convert [String String] void]
;			]))

(require '[clojure.tools.cli :as c])

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

(defn convert-files [arg-map]
  "Apply the conversion stylesheet to the input files"
  (let [output-dir (:outputdir arg-map)]
	  (if (:single arg-map)
		  (doall (map #(spit (str output-dir (.getName %)) (convert %)) (input-files (:inputdir arg-map))))
		  (doall (pmap #(spit (str output-dir (.getName %)) (convert %)) (input-files (:inputdir arg-map))))))) 

(defn -main [& args]
  "Process command-line switches and call main conversion function"
  (let [opts (c/cli args
    (c/optional ["-s" "--single" "Run in single-threaded mode" :default false]) 
    (c/optional ["-i" "--inputdir" "Input directory path" :default (str abbot-home "/input")])
    (c/optional ["-o" "--outputdir" "Output directory path" :default (str abbot-home "/output/")]))]
    (convert-files opts)))

(defn -init []
	[[] (atom [])])

(defn -convert [this input output]
  (let [args {:inputdir input
              :outputdir output
              :single false}]
    (convert-files args)))
