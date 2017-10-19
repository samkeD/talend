%~d0
cd %~dp0
java -Xms256M -Xmx512M -cp .;../lib/routines.jar;../lib/dom4j-1.6.1.jar;../lib/log4j-1.2.16.jar;../lib/talend_file_enhanced_20070724.jar;../lib/talendcsv.jar;fuzzyparent_0_1.jar;fuzzy_0_1.jar; big_data_talend_demo.fuzzyparent_0_1.FuzzyParent --context=Default %* 