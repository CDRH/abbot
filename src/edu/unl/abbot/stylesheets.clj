
;;; stylesheets.clj 
;;;
;;; This file is part of Abbot.  It loads the metastylesheet,
;;; XML Schema, and abbot_config.xml files, and generates the
;;; conversion stylesheet.
;;;
;;; Written and Maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities, University
;;; of Nebraska-Lincoln.
;;;
;;; Last Modified: Sun Feb 26 15:43:04 CST 2012
;;;
;;; Copyright © 2011 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
;;; for more details.

(ns edu.unl.abbot.stylesheets
	(import
		(java.io File))
  (:use edu.unl.abbot.utils)
	(:require [saxon :as sax]))

;; Returns a closure for creating the conversion stylesheet (the stylesheet
;; that does the actual conversion) from the meta-stylesheet.
;;
;; Templates from abbot_config.xml are read into the meta-stylesheet
;; at runtime (by the meta-stylesheet itself).

(defn conversion-stylesheet [schema]
  (let [rng-file (sax/compile-xml (urlify schema))
        meta-url "http://abbot.unl.edu/metaStylesheetForRNGschemas.xsl"
        meta-stylesheet (sax/compile-xslt (java.net.URL. meta-url))]
    (fn [x] (sax/compile-xslt (meta-stylesheet rng-file)) x)))


(defn convert [stylesheet xml-file]
  (let [xmlfile (sax/compile-xml xml-file)]
    (stylesheet xmlfile)))
