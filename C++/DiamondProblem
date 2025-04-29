//using the required libraries
#include <iostream>
#include <vector>
#include <type_traits>
#include <assert.h>
#include <algorithm>
//namespace std for the whole code
using namespace std;

/*
 * class Person
 * It is the parent class for the Employees and student
 */
class Person{
public:
    string fname, lname; // string field for the First name and Lastname(surname)
    static int count; //static counter for persons -
    Person(string& f, string& l) : fname(f), lname(l) { //parametrize constructor
        count ++; //it increases, when we creat child classes
    }
    virtual string getEmail() = 0; // purely virtual function
    virtual ~Person() = default; // virtual destructor
    Person& operator=(const Person&) = delete; //deleted assignment operator


};
class Student : public virtual Person{ // child class Student. It inherits virtual Person class
public:
    Student(string& fname, string& lname) : Person(fname, lname) {} // parametrize constructor inherits the Person constructor
    string getEmail() override { // overriding the virtual function
        // concatenating the strings that produce the email address
        string email = "";
        email += tolower(fname.at(0));
        email += ".";
        email += lname;
        email += "@students.org";
        transform(email.begin(), email.end(), email.begin(), ::tolower);
        return email; // return the email address
    }
};
class Employee : public virtual Person{ //child class Employee inherits the virtual Person class
public:
    Employee(string& fname, string& lname) : Person(fname, lname) {}; // parametrize constructor that inherits Person constructor
    string getEmail() override {// overriding the virtual function
        // concatenating the strings that produce the email address

        string email = "";
        email += tolower(fname.at(0));
        email += ".";
        email += lname;
        email += "@employees.org";
        transform(email.begin(), email.end(), email.begin(), ::tolower);
        return email; // return the email address
    }
};
class TA :  public Student, public  Employee{ // It inherits both Student and Employee classes
public:
    //constructor inherits all 3 constructor, since the Person is virtual class
    TA(string fname, string lname) : Person(fname, lname), Employee(fname, lname), Student(fname, lname) {
    }
    string getEmail() override {// overriding the virtual function
        // concatenating the strings that produce the email address
        string email = "";
        email += tolower(fname.at(0));
        email += ".";
        email += lname;
        email += "@employees.org";
        transform(email.begin(), email.end(), email.begin(), ::tolower);
        return email; // return the email address
    }
};
// initialization the initial value for counting the persons
int Person :: count = 0;
int main() {
    static_assert(is_abstract_v<Person>); // check if the Person class is virtual
    static_assert(is_base_of_v<Person, Employee>); //check is the Employee is child class
    static_assert(is_base_of_v<Person, Student>); //check is the Student is child class
    static_assert(is_base_of_v<Person, TA>); //check is the TA is child class
    static_assert(is_base_of_v<Employee, TA>);//check is the TA is child class for the Employee
    static_assert(is_base_of_v<Student, TA>);//check is the TA is child class for the Student
    static_assert(is_polymorphic_v<Student>); //check is getEmail() override or not
    static_assert(is_polymorphic_v<Employee>);//check is getEmail() override or not
    static_assert(is_polymorphic_v<TA>);//check is getEmail() override or not
    static_assert(!is_copy_constructible_v<Person>); // check can we copy the Person class or not
    static_assert(has_virtual_destructor_v<Person>); // check does the Person has a virtual destructor
    //declaration First name and surname as strings
    string fname, lname;
    //input the info
    cin >> fname >> lname;
    Student* s = new Student(fname, lname); // creating an instance of the class Student
    assert(Person::count == 1); //check is the value count was incremented or not

    cin >> fname >> lname;
    Employee* e = new Employee(fname, lname);// creating an instance of the class Employee
    assert(Person::count == 2); //check is the value count was incremented or not

    cin >> fname >> lname;
    TA* t = new TA(fname, lname);// creating an instance of the class TA
    assert(Person::count == 3);//check is the value count was incremented or not

    vector<Person*> people = {s, e, t}; // creating the vector of the classes
    for(auto& p: people) { // go throw the entire vector
        cout << p->getEmail() << endl; // print the email
        delete p; // As we crate the virtual destructor, we can apply it for the whole child classes
    }
}
