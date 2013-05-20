
;;; core.clj
;;;
;;; This file is part of Abbot.  It contains main and associated
;;; functions.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Mon May 20 18:33:02 CDT 2013
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
    (java.io File)
    (java.nio.file Files Paths))
  (:use edu.unl.abbot.stylesheets)
  (:use edu.unl.abbot.utils)
  (:use clojure.xml)
  (:use clojure.java.io)
  (:use [clojure.java.shell :only (sh)])
  (:use [clojure.tools.logging :only (info error fatal)])
  (:gen-class))

(require '[clojure.xml :as xml])
(require '[clojure.java.io :as io])

(declare converter)
(declare input-files)

(defn convert-files [{input-dir  :inputdir
              output-dir :outputdir
              custom     :custom
              namespace  :namespace
              single     :single
              target     :target}]
  "Apply the conversion stylesheet to the input files."
  (if (and (.isDirectory (File. input-dir)) (.isDirectory (File. output-dir)))
    ; n, c, and t are stylesheet parameters
    (let [params (hash-map :n namespace :c custom :t target)
                stylesheet (conversion-stylesheet target params)
                converter (converter output-dir stylesheet)
    input (input-files input-dir)]
      (try
        (info "Starting job")
        (if single
          (doall (map converter input)) 
          (doall (pmap converter input)))
        (info "Job ended -- shutting down")
        (catch Exception ex
          (error ex "There was an error during file processing"))))
  ; else
  (do
    (println "No input/output directories specified (-h for details)")
    (fatal "No input/output directories specified (-h for details)"))))

(defn converter [output-dir stylesheet]
    "Return a function that runs the conversion and writes out the file"
    ; Written as a clojure to keep the main convert-files function
    ; uncluttered.  
    (fn [x] (spit (str output-dir (.getName x)) (apply-master stylesheet x))))

(defn input-files [input-dir]
  "Read input files and do some basic sanity checking."
  ;; Apparently, the only truly reliable way to check that a text file
  ;; is indeed an XML file is to parse it.  The XML declaration is
  ;; optional, and different legal unicode encodings may or may not
  ;; have a byte order mark.  The .xml extension is everywhere used
  ;; in the specification, but nowhere mandated.
  ;;
  ;; So we check that the file is, in fact, a file, demand an .xml
  ;; extension, and, if the user has passed the quarantine switch,
  ;; parse the file and see if it validates.  If the user doesn't
  ;; opt to quarantine invalid files, errors are left to Saxon.
  (let [has-xml? [#(.isFile %) #(has-xml-extension? %)]
        files (file-seq (File. input-dir))]
    (filter (fn [x] (every? #(% x) has-xml?)) files))) 
