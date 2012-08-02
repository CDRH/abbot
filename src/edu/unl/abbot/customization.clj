
;;; customization.clj 
;;;
;;; This file is part of Abbot.  It loads the customization
;;; file and parses its content for inclusion in the meta stylesheet.
;;;
;;; Written and Maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities, University
;;; of Nebraska-Lincoln.
;;;
;;; Last Modified: Thu Aug 02 15:26:28 CDT 2012
;;;
;;; Copyright © 2011-2012 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
;;; for more details.

(ns edu.unl.abbot.customization
	(import
		(java.io File))
  (:use edu.unl.abbot.utils)
	(:use clojure.data.xml)
	(:require [saxon :as sax]))

(defn meta-stylesheet [config]
	(let [meta-url "http://abbot.unl.edu/metaStylesheetForRNGschemas-test.xsl"
			  abbot_config (urlify config)]
				(sax/compile-xslt (java.net.URL. meta-url)


		


