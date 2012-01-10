
;;; stylesheets.clj 
;;;
;;; This file is part of Abbot.  It loads the metastylesheet, XML Schema,
;;; and abbot_config.xml files, and generates the conversion stylesheet.
;;;
;;; Written and Maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities, University
;;; of Nebraska-Lincoln.
;;;
;;; Last Modified: Wed Oct 26 15:18:50 CDT 2011
;;;
;;; Copyright © 2011 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
;;; for more details.

(ns edu.unl.abbot.stylesheets
  (:use edu.unl.abbot.utils))

(require '[saxon :as xml])

;; Creates the "conversion stylesheet" (the stylesheet that does the
;; actual conversion) from the "meta-stylesheet"
;;
;; Templates from abbot_config.xml are read into the meta-stylesheet
;; at runtime (by the meta-stylesheet itself).

(def conversion-stylesheet
  (let [rng-file (xml/compile-xml (slurp "target/tei-xl.rng"))
        meta-file (slurp "xslt/metaStylesheetForRNGschemas.xsl")
        meta-stylesheet (xml/compile-xslt meta-file)]
    (xml/compile-xslt (meta-stylesheet rng-file))))

(defn convert [xml-file]
  (let [xmlfile (xml/compile-xml xml-file)]
    (conversion-stylesheet xmlfile)))
