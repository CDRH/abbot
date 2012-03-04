(defproject edu.unl/abbot "0.4.2"
  :description "Abbot: A Conversion Tool for Text Interoperability"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/tools.cli "0.2.1"]
                 ;[org.clojars.ninjudd/data.xml "0.0.1-SNAPSHOT"]
                 [clojure-saxon "0.9.2"]]
	:keep-non-project-classes true
	:main edu.unl.abbot.cli
	:aot [edu.unl.abbot.Abbot])
