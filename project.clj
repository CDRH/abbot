(defproject edu.unl/abbot "0.4.8"
  :description "Abbot: A Conversion Tool for Text Interoperability"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.1"]
                 [org.clojure/data.xml "0.0.6"]
                 [clojure-saxon "0.9.2"]]
	:keep-non-project-classes true
	:main edu.unl.abbot.cli
	:aot [edu.unl.abbot.Abbot])
