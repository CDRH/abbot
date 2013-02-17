(defproject edu.unl/abbot "0.7.1"
  :description "Abbot: A Conversion Tool for Text Interoperability"
  :dependencies [[org.clojure/clojure "1.5.0-RC16"]
                 [org.clojure/tools.cli "0.2.2"]
                 [org.clojure/data.xml "0.0.7"]
                 [clojure-saxon "0.9.3"]]
	:keep-non-project-classes true
	:main edu.unl.abbot.cli
	:aot [edu.unl.abbot.Abbot])
