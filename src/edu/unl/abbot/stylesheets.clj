
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
;;; Last Modified: Fri Jan 20 16:54:15 CST 2012
;;;
;;; Copyright © 2011 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
;;; for more details.

(ns edu.unl.abbot.stylesheets
  (:use edu.unl.abbot.utils)
	(:require [saxon :as sax])
	(:require [clojure.xml :as xml])
	(:require [clojure.zip :as zip])
	(:require [clojure.contrib.zip-filter.xml :as zf])
	(:require [clojure.contrib.prxml :as px]))

;; Creates the "conversion stylesheet" (the stylesheet that does the
;; actual conversion) from the "meta-stylesheet"
;;
;; Templates from abbot_config.xml are read into the meta-stylesheet
;; at runtime (by the meta-stylesheet itself).

(defn stylesheet [schema config]
  (let [meta-url "http://abbot.unl.edu/metaStylesheetForRNGschemas.xsl"
				rng-file (sax/compile-xml (slurp schema))
				config-file (xml/parse config)
        meta-file (xml/parse meta-url)]
		(px/prxml meta-file)
		(sax/compile-xslt (sax/compile-xslt (px/prxml (with-out-str meta-file))) rng-file)))

(defn convert [conversion-stylesheet xml-file]
  (let [xmlfile (sax/compile-xml xml-file)]
    (conversion-stylesheet xmlfile)))
