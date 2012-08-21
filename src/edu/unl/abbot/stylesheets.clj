
;;; stylesheets.clj 
;;;
;;; This file is part of Abbot.  It loads the metastylesheet
;;; and XML Schema and generates the conversion stylesheet.  The
;;; customization file is loaded and parsed by customization.clj
;;;
;;; Written and Maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities, University
;;; of Nebraska-Lincoln.
;;;
;;; Last Modified: Mon Aug 06 17:13:22 CDT 2012
;;;
;;; Copyright © 2011-2012 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See COPYING for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See COPYING
;;; for more details.

(ns edu.unl.abbot.stylesheets
	(:import
		(java.io InputStreamReader
						 FileInputStream))
  (:use edu.unl.abbot.utils)
	(:use clojure.data.xml)
	(:require [saxon :as sax])
	(:require [clojure.java.io :as io]))

(defn create-meta-stylesheet []
	(let [meta-url "http://abbot.unl.edu/metaStylesheetForRNGschemas.xsl"]
				;meta-input (java.io.StringReader. (slurp meta-url))
				;custom-input (java.io.InputStreamReader. (java.io.FileInputStream. custom) "UTF-8")
				;meta-map (parse meta-input)
	      ;custom-map (parse custom-input)]
		;(.close custom-input)
		(fn [x] ((sax/compile-xslt (java.net.URL. meta-url)) x))))

;; Creates the conversion stylesheet (the XSLT that does the actual
;; conversion) from the meta-stylesheet, and returns it as a function.
;;
;; Templates from abbot_config.xml are read into the meta-stylesheet
;; at runtime (by the meta-stylesheet itself).

(defn conversion-stylesheet [schema]
  "Returns the conversion stylesheet (as a function)"
  (let [rng-file (sax/compile-xml (urlify schema))
        meta-stylesheet (create-meta-stylesheet)
				conversion-xslt (sax/compile-xslt (meta-stylesheet rng-file))]
    (fn [x] (conversion-xslt x))))


(defn apply-master [stylesheet xml-file]
  "Apply master stylesheet to individual XML file."
  (with-open [rdr (io/reader xml-file)]
		(let [xml (sax/compile-xml rdr)]
			(stylesheet xml))))
