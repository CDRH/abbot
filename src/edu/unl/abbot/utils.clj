
;;; utils.clj 
;;;
;;; This file is part of Abbot.  It contains some general utility
;;; functions and variables.
;;;
;;; Written and Maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities, University
;;; of Nebraska-Lincoln.
;;;
;;; Last Modified: Wed Oct 26 15:26:55 CDT 2011
;;;
;;; Copyright © 2011 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
;;; for more details.

(ns edu.unl.abbot.utils)

(def abbot-home (System/getenv "ABBOT_HOME"))

(defn has-xml-extension? [file] 
  "Simple substring check for the presence of a .xml extension on
  the filename."
	(let [filename (.getName file)]
		(= ".xml" (.substring filename (.lastIndexOf filename ".")))))
