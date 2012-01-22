
;;; cli.clj
;;;
;;; This file is part of Abbot.  It contains the entry point for
;;; the command-line interface.
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

(ns edu.unl.abbot.cli
	(:use edu.unl.abbot.core)
  (:use edu.unl.abbot.stylesheets)
  (:use edu.unl.abbot.utils)
  (import
    (java.io File)) 
		(:gen-class))

(require '[clojure.tools.cli :as c])

(defn -main [& args]
  "Process command-line switches and call main conversion function"
  (let [opts (c/cli args
    (c/optional ["-c" "--config" "Abbot config" :default "http://abbot.unl.edu/abbot_config.xml"])
    (c/optional ["-t" "--schema" "Target schema" :default "http://abbot.unl.edu/tei-xl.rng"])
    (c/optional ["-s" "--single" "Run in single-threaded mode" :default false]) 
    (c/optional ["-i" "--inputdir" "Input directory path" :default (str abbot-home "/input")])
    (c/optional ["-o" "--outputdir" "Output directory path" :default (str abbot-home "/output/")]))]
    (convert-files opts)))
