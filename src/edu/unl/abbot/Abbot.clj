
;;; Abbot.clj
;;;
;;; This file is part of Abbot.  It contains the Java API.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Mon Aug 06 17:13:09 CDT 2012
;;;
;;; Copyright © 2011-2012 Board of Regents of the University of Nebraska-
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
  	:methods [
			[convert [String String] void]
			[convert [String String String] void]
			]))

(defn -init []
	[[] (atom [])])

(defn -convert
	"Apply the conversion stylesheet to the input files."
	([this inputdir outputdir]
		(let [opts {:inputdir inputdir
								:outputdir outputdir
								:schema "http://abbot.unl.edu/tei-xl.rng"}]
			(convert-files opts)))
	([this inputdir outputdir schema]
	(let [opts {:inputdir inputdir
			  			:outputdir outputdir
							:schema schema}] 
			(convert-files opts))))
