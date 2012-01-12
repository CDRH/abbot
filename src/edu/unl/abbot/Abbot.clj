
;;; Abbot.clj
;;;
;;; This file is part of Abbot.  It contains the Java API.
;;;
;;; Written and maintained by Brian Pytlik-Zillig and Stephen Ramsay
;;; for the Center for Digital Research in the Humanities at the
;;; University of Nebraska-Lincoln.
;;;
;;; Last Modified: Thu Jan 12 12:59:54 CST 2012
;;;
;;; Copyright © 2011 Board of Regents of the University of Nebraska-
;;; Lincoln (and others).  See LICENSE for details.
;;;
;;; Abbot is distributed in the hope that it will be useful, but
;;; WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See LICENSE
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
			]))

(defn -init []
	[[] (atom [])])

(defn -convert [this inputdir outputdir]
	(let [opts {:inputdir inputdir
			  			:outputdir outputdir}]
    (convert-files opts)))
