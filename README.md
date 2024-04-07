<h1>OleksandrOrlovIT ProfitSoft JavaCore</h1>
<h2>Project Description</h2>
<h3>Summary</h3>
This is a console-driven application, which is used to read some .json files of City(cityName, country, cityPopulation, cityArea, foundedAt, languages) object from directory, and to form a statistics by inputed attributes using .xml files.
<h3>Tech Stack</h3>
<p>Java 17, lombok, jackson-core, jackson-dataformat-xml, junit-jupiter, mockito-core, jacoco-maven-plugin, maven-surefire-plugin.</p>
<h3>Main working classes</h3>
<h4>InputStreamJsonArrayStreamDataSupplier<T> implements Supplier<Stream<T>></h4>
<p>
  This class is used to create a stream of json objects.
  
  ![image](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/75e46f5c-2edc-4c94-9c23-39bda35b87af)

</p>
<h4>JSONParserV2</h4>
<p>
  A utility class for parsing JSON data from multiple files in parallel and extracting statistics.
  
  ![image](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/3d440e4c-a451-46da-896f-2f4cfe773aa8)

</p>
<h4>XMLConverter</h4>
<p>
  Utility class for converting maps to XML format.
  
 ![image](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/6a763f4e-c1f7-4849-b542-705f179eed7e)


</p>
  
<h3>Features</h3>
<p>Using main method of the Main.java you can start the application. After starting Main.main() and inputing  number of threads, path to directory with json files, list of fieldName and path to directory where xml files will be saved, your statistics will be created in the outputDirectory
  
![image](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/8ca0b949-d288-473e-9d99-124e59293560)

<br/>
Code contains PopulateJSONCities.java which can be used to create and populate .json file with jsons of the cities.

![image](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/42d4882e-1e18-43d4-aed1-62d9e9850e83)
</p>
<h3>Testing</h3>
<h4>Code tests</h4>
<p>
  Using JUnit, Mockito and Jacoco for code coverage, I got 70+ % for test coverage.

  ![image](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/11256873-185a-42f8-8d87-0fadcbbd287e)

</p>
<h4>Speed test</h4>
<p>
  For the speed test with different amount of threads, I created a separated test which is not tested by default mvn test command.

  ![Screenshot from 2024-04-07 02-16-29](https://github.com/OleksandrOrlovIT/ProfitSoftJavaCore/assets/86959421/e474b3f7-1977-46b9-9507-f07082783742)
  
  As you can see difference between using different amount of threads is not crucial for my application
</p>
<h4>How to Use</h4>
<p>In order to use my application simply clone from github this repository and start main method of Main class. Json files have to be valid</p>
