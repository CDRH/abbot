
;;; cli.clj
;;;
;;; This file is part of Abbot.  It contains the entry point for
;;; the command-line interface.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Wed Mar 13 17:42:02 CDT 2013
;;;
;;; Copyright © 2011-2013 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See COPYING for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See COPYING
;;; for more details.

(ns edu.unl.abbot.cli
  (:import
    (java.io File)) 
	(:use edu.unl.abbot.core)
  (:use edu.unl.abbot.stylesheets)
  (:use edu.unl.abbot.utils)
  (:use [clojure.tools.logging :only (fatal)])
	(:gen-class))

(require '[clojure.tools.cli :as c])

(defn -main [& args]
  "Process command-line switches and call main conversion function"
  (if abbot-home
    (let [opts (c/cli args
      ["-c" "--custom" "Abbot customization" :default "http://abbot.unl.edu/abbot_config.xml"]
      ["-h" "--help" "This usage message" :flag true]
      ["-i" "--inputdir" "Input directory path" :default (str abbot-home "/input")]
      ["-n" "--namespace" "Namespace for metastylesheet" :default "http://www.tei-c.org/ns/1.0"]
      ["-s" "--single" "Run in single-threaded mode" :default false] 
      ["-t" "--target" "Target schema" :default "http://abbot.unl.edu/tei-xl.rng"]
      ["-o" "--outputdir" "Output directory path" :default (str abbot-home "/output/")]
      ["-V" "--version" "Show version number" :flag true])
		  options (first opts)
		  banner  (last opts)]
		  (cond
			  (:version options) (do
				  (println (format "Version %s", version))
				  (System/exit 0))
			  (:help options) (do (println banner))
			  :else
          (convert-files options)))
    (do
      (println "Please set ABBOT_HOME to the directory containing the abbot jarfile")
      (fatal "ABBOT_HOME not set")))
  (shutdown-agents))
