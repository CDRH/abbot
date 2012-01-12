(defproject edu.unl/abbot "0.2.1"
  :description "Abbot: A Conversion Tool for Text Interoperability"
  :dependencies [[org.clojure/clojure "1.2.1"]
								 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojure/tools.cli "0.1.0"]
                 [clojure-saxon "0.9.2"]]
	:keep-non-project-classes true
	:main edu.unl.abbot.cli
	:aot [edu.unl.abbot.Abbot])
