Description 
 
The exercise is to write a​ ​command-line​ driven text search engine, with usage being:  
java -jar jarName mainClassFile directoryContainingTextFiles  
This should read all the text files in the given directory, building an ​in-memory 
​ representation of the files and their contents, and then give a command prompt at which interactive searches can be performed.  
An example session might look like:  
$ java -jar SimpleSearch.jar Searcher /foo/bar  14 files read in directory /foo/bar  search> to be or not to be  file1.txt : 100%  file2.txt : 90%  search>  search> cats  no matches found  search> :quit  $  
 
The search should take the words given on the prompt and return a list of the top 10 (maximum) matching                    filenames in rank order, giving the rank score against each match. 