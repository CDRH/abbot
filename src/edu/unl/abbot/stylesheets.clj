
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
;;; Last Modified: Sun Mar 17 15:44:31 CDT 2013
;;;
;;; Copyright © 2011-2013 Board of Regents of the University of Nebraska-
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
    (:use [clojure.tools.logging :only (error)])
    (:require [saxon :as sax])
    (:require [clojure.java.io :as io]))

(defn create-meta-stylesheet [params]
  (try
    (let [meta-url "http://abbot.unl.edu/metaStylesheetForRNGschemas.xsl"
          meta-stylesheet (sax/compile-xslt (java.net.URL. meta-url))]
      (fn [x] (meta-stylesheet x params)))
    (catch Exception ex
      (error ex "Unable to create meta-stylesheet"))))

;; Creates the conversion stylesheet (the XSLT that does the actual
;; conversion) from the meta-stylesheet, and returns it as a function.
;;
;; Templates from abbot_config.xml are read into the meta-stylesheet
;; at runtime (by the meta-stylesheet itself).

(defn conversion-stylesheet [target params]
  "Returns the conversion stylesheet (as a function)"
  (try
    (let [rng-file (sax/compile-xml (urlify target))
          meta-stylesheet (create-meta-stylesheet params)
          conversion-xslt (sax/compile-xslt (meta-stylesheet rng-file))]
      (fn [x] (conversion-xslt x)))
    (catch Exception ex
      (error ex "Unable to create conversion stylesheet"))))


(defn apply-master [stylesheet xml-file]
  "Apply master stylesheet to individual XML file."
  (with-open [rdr (io/reader xml-file)]
    (let [xml (sax/compile-xml rdr)]
      (stylesheet xml))))
