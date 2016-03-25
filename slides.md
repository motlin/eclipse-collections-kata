Eclipse Collections Kata
========================


What is Eclipse Collections?
-----------------------
A Java Collections Library.

What is a Code Kata?
--------------------

A programming exercise which helps hone your skills through practice.


What is Eclipse	Collections?
----------------------------

https://www.eclipse.org/collections/

* The best Java collections framework ever that brings happiness to your Java development :)
* A supplement or replacement for the Java Collections Framework (JCF).
* "Inspired by" Smalltalk Collection protocol.
* Rich, concise, readable APIs
* Containers including immutable collections, primitive collections, bimaps, multimaps, and bags


What is a Code Kata?
--------------------
* A programming exercise which helps hone your skills through practice.
* This one is set up as a series of unit tests which fail.
* Your task is to make them pass, using Eclipse Collections.

> I hear and I forget.
  I see and I remember.
  I do and I understand.
  -- Confucius


Eclipse Collections Code Kata
-----------------------------

* New concepts are introduced in the slides
* Coding exercises are at the end of each section



Kata Set-up
-----------
* Clone the [Eclipse Collections repo](https://github.com/eclipse/eclipse-collections-kata) or simply download the [master zip file](https://github.com/eclipse/eclipse-collections-kata/archive/master.zip) and extract it.
* Run the gradle init script for your IDE.
  * `gradlew eclipse`
  * `gradlew idea`
* Open your IDE and run the tests under `company-kata/src/tests`.
* The tests are expected to fail. Your task is to make them pass, using Eclipse Collections.



Iteration Patterns
==================


What is an iteration pattern?
-----------------------------

* Sort is one example.
* We want to sort a list of people by last name, first name.
* Which method in the JCF can we use?

```
Person john = new Person("John", "Smith");
Person jane = new Person("Jane", "Smith");
Person z = new Person("Z.", "Jones");
List<Person> people = new ArrayList<Person>();
people.add(john);
people.add(jane);
people.add(z);
```



Sort
----

```
public static void java.util.Collections.sort(
  List<T> list,
  Comparator<? super T> c)
```

Javadoc
-------

> Sorts the specified list according to the order induced by the specified comparator.
> All elements in the list must be mutually comparable.


Sort: Anonymous inner class
---------------------------

```
Collections.sort(people, new Comparator<Person>() {
  public int compare(Person o1, Person o2) {
	int lastName = o1.getLastName().compareTo(o2.getLastName());
	if (lastName != 0) {
	  return lastName;
	}
    return o1.getFirstName().compareTo(o2.getFirstName());
  }
});
```


Sort: Lambda
------------

```
Collections.sort(people, (Person o1, Person o2) -> {
  int lastName = o1.getLastName().compareTo(o2.getLastName());
  if (lastName != 0) {
	return lastName;
  }
  return o1.getFirstName().compareTo(o2.getFirstName());
});
```


Collect Pattern
---------------
* _Collect_ Pattern (a.k.a. _map_ or _transform_).
* Return a new element where each element has been transformed.
* e.g. collect each pet's name.
* __Function__ is the type that takes an object and returns an object of a different type.
* a.k.a. _Transformer_.


Collect Pattern (legacy for loop)
---------------------------------

```
List<Pet> pets = someCodeToGetPets();
List<String> petNames = new ArrayList<String>();	
for (Pet pet : pets)	
{
petNames.add(pet.getName());	
}
```


Collect Pattern (Eclipse Collections)
-------------------------------------

Using Java 8 lambda expression
```
MutableList<Pet> pets = someCodeToGetPets();
MutableList<String> petNames = pets.collect(pet -> pet.getName());	
```

Or using method reference
```
MutableList<Pet> pets = someCodeToGetPets();
MutableList<String> petNames = pets.collect(Pet::getName);	
```


Select Pattern
--------------
* _Select_ Pattern (a.k.a. _filter_).
* Return the elements of collections that satisfy some condition
* e.g. select only those people who have a pet.
* __Predicate__ is the type that takes an object and returns a boolean


Select Pattern (Eclipse Collections)
------------------------------------

```
MutableList<Person> people = someCodeToGetPeople();
MutableList<Person> petPeople 
    = people.select(person -> person.isPetPerson());	
```


Exercise 1
----------
* Fix `Exercise1Test`; they have failures.
* Figure out how to get the tests to pass using what you have seen so far.



Ex1 solutions
=============

*go down to learn Ex1 solutions*

*go right to learn Ex2*


Get first names of people
-------------------------
```
@Test
public void getFirstNamesOfAllPeople()
{
    MutableList<Person> people = this.people;
    MutableList<String> firstNames = 
        people.collect(Person::getFirstName);
    MutableList<String> expectedFirstNames = 
        Lists.mutable.with("Mary", "Bob", "Ted", "Jake", "Barry", "Terry", "Harry", "John");
    Assert.assertEquals(expectedFirstNames, firstNames);
}
```


Get names of Mary Smith's Pets
------------------------------
```
@Test
public void getNamesOfMarySmithsPets()
{
    Person person = this.getPersonNamed("Mary Smith");
    MutableList<Pet> pets = person.getPets();
    MutableList<String> names = 
        pets.collect(eachPet -> eachPet.getName()); 
    Assert.assertEquals("Tabby", names.makeString());
}
```


Get people with cats
--------------------
```
@Test
public void getPeopleWithCats()
{
    MutableList<Person> people = this.people;
    MutableList<Person> peopleWithCats = 
        people.select(person -> person.hasPet(PetType.CAT));
    Verify.assertSize(2, peopleWithCats);
}
```


Get people without cats
-----------------------
```
@Test
public void getPeopleWithoutCats()
{
    MutableList<Person> people = this.people;
    MutableList<Person> peopleWithoutCats = 
        people.reject(person -> person.hasPet(PetType.CAT));
    Verify.assertSize(6, peopleWithoutCats);
}
```



Ex2
===

*go down to learn new concepts in Ex2*

*go right to learn Ex2 solutions*
