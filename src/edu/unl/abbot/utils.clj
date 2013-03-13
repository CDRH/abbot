
;;; utils.clj 
;;;
;;; This file is part of Abbot.  It contains some general utility
;;; functions and variables.
;;;
;;; Written and Maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities, University
;;; of Nebraska-Lincoln.
;;;
;;; Last Modified: Wed Mar 13 17:57:41 CDT 2013
;;;
;;; Copyright © 2011-2013 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See COPYING for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See COPYING
;;; for more details.

(ns edu.unl.abbot.utils
	(:import
		(java.io File)))

(def version "0.8.0")

(def abbot-home (System/getenv "ABBOT_HOME"))

(defn has-xml-extension? [file] 
  "Simple substring check for the presence of a .xml extension on
  the filename."
	(let [filename (.getName file)]
		(= ".xml" (.substring filename (.lastIndexOf filename ".")))))

(defn urlify [uri]
	"URL-ify pathnames"
	(if (.. (File. uri) isFile)
		(.. (File. uri) toURL)
		(java.net.URL. uri)))
