
;;; Abbot.clj
;;;
;;; This file is part of Abbot.  It contains the Java API.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Fri Jan 11 19:48:21 CST 2013
;;;
;;; Copyright © 2011-2013 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See COPYING for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See COPYING
;;; for more details.

(ns edu.unl.abbot.Abbot
	(:use edu.unl.abbot.core)
  (:use edu.unl.abbot.stylesheets)
  (:use edu.unl.abbot.utils)
  (import
    (java.io File))
    (:gen-class
    :name edu.unl.abbot.Abbot
    :init init
    :state state
    :methods [[convert [String String String] void]
              [convert [String String String String] void]
              [convert [String String String String String] void]]))

(defn -init []
	[[] (atom [])])

(defn -convert
	"Apply the conversion stylesheet to the input files."
	([this inputdir outputdir target]
		(let [opts {:inputdir inputdir
                :outputdir outputdir
                :target target
                :namespace "http://www.tei-c.org/ns/1.0"
                :custom "http://abbot.unl.edu/abbot_config.xml"}]
			(convert-files opts)))
	([this inputdir outputdir target namespace]
    (let [opts {:inputdir inputdir
                :outputdir outputdir
                :target target
                :namespace namespace
                :custom "http://abbot.unl.edu/abbot_config.xml"}]
			(convert-files opts)))
	([this inputdir outputdir target namespace custom]
    (let [opts {:inputdir inputdir
                :outputdir outputdir
                :target target
                :namespace namespace
                :custom custom}]
			(convert-files opts))))
