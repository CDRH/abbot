
;;; cli.clj
;;;
;;; This file is part of Abbot.  It contains the entry point for
;;; the command-line interface.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Mon Aug 06 17:13:01 CDT 2012
;;;
;;; Copyright © 2011-2012 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See COPYING for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See COPYING
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
    ["-c" "--custom" "Abbot customization" :default "http://abbot.unl.edu/abbot_config.xml"]
    ["-t" "--schema" "Target schema" :default "http://abbot.unl.edu/tei-xl.rng"]
    ["-s" "--single" "Run in single-threaded mode" :default false] 
    ["-i" "--inputdir" "Input directory path" :default (str abbot-home "/input")]
    ["-o" "--outputdir" "Output directory path" :default (str abbot-home "/output/")]
    ["-h" "--help" "This usage message" :flag true]
    ["-V" "--version" "Show version number" :flag true])
		options (first opts)
		banner  (last opts)]
		(cond
			(:version options) (do
				(println (format "Version %s", version))
				(System/exit 0))
			(:help options) (do (println banner))
			:else (convert-files options)))
			(shutdown-agents))
