#include <iostream>
#include <string>
#include <cstdio>
#include <vector>
#include <ranges>
#include <bits/unique_ptr.h>
#include <cmath>
#include <algorithm>
// connect all required libraries
using namespace std;
// the parent class Animal
class Animal{
    //name and number of days for animal
private:
    const string name;
    int daysLived;
public:
    Animal() = default; // destructor
    Animal(const string& name, int daysLived) : name(name), daysLived(daysLived) {
    }; //parametrize constructor
    Animal(Animal& other) : name(other.getName()), daysLived(other.getDaysLived()){

    };//copy constructor
    virtual ~Animal() = default; // virtual destructor because it contain child classes
    int getDaysLived(){ // since amount of days is private -> write getter
        return daysLived;
    }
    string getName(){// since name is private -> write getter
        return name;
    }
    void sayName(){ // print required output
        printf("My name is %s, days lived: %d\n", name.c_str(), daysLived);
    }
    virtual void attack(Animal& other) = 0; // pure virtual method -> this class if abstract
    void setDaysLived(int newValue){ // since amount of days is private -> write setter
        daysLived = newValue;
    }
};
//child class Fish -> inherits Animal class virtually
class Fish : virtual public Animal{
public:
    Fish(const string name, int daysLived) : Animal(name, daysLived){}; // constructor for Fish class
    void attack(Animal& other) override{ // override pure virtual method from Animal class
        other.setDaysLived(0); // set for attacked animal zero days of living -> it dies
    }
};
//child class BetterFish -> inherits Fish class
class BetterFish :  public Fish{
public:
    BetterFish(string name, int daysLived) : Animal(name, daysLived),  Fish(name, daysLived){}; // constructor for BetterFish class
    explicit BetterFish(Fish& fish) : Animal(fish.getName(), fish.getDaysLived()), Fish(fish.getName(), fish.getDaysLived()){}
    void attack(Animal& other) override{// override pure virtual method from Animal class
        other.setDaysLived(0);// set for attacked animal zero days of living -> it dies

    };

};
//child class Bird -> inherits Animal class virtually
class Bird : virtual public Animal{
public:
    Bird(const string name, int daysLived) : Animal(name, daysLived){};// constructor for Bird class
    void attack(Animal& other) override {// override pure virtual method from Animal class
        other.setDaysLived(0);// set for attacked animal zero days of living -> it dies
    }
};
//child class BetterBird -> inherits Bird class
class BetterBird : public Bird{
public:
    BetterBird(string name, int daysLived) : Animal(name, daysLived), Bird(name, daysLived){};//constructor for BetterBird class
    explicit BetterBird(Bird& bird) : Animal(bird.getName(), bird.getDaysLived()), Bird(bird.getName(), bird.getDaysLived()){}
    void attack(Animal& other) override{
        other.setDaysLived(0);// set for attacked animal zero days of living -> it dies
    };

};
//child class Mouse -> inherits Animal class virtually
class Mouse : virtual public Animal{
public:
    Mouse(const string name, int daysLived) : Animal(name, daysLived){}; // constructor for Mouse class
    void attack(Animal& other) override{// override pure virtual method from Animal class
        other.setDaysLived(0);// set for attacked animal zero days of living -> it dies
    }
};
//child class BetterMouse -> inherits Mouse class
class BetterMouse :  public Mouse{
public:
    BetterMouse(string name, int daysLived) : Animal(name, daysLived),  Mouse(name, daysLived){}; //constructor for Monster class
    explicit BetterMouse(Mouse& mouse) : Animal(mouse.getName(), mouse.getDaysLived()), Mouse(mouse.getName(), mouse.getDaysLived()){}
    void attack(Animal& other) override{// override pure virtual method from Animal class
        other.setDaysLived(0);// set for attacked animal zero days of living -> it dies

    };

};
//class Monster inherits 3 classes at the same time
class Monster : public BetterBird, public BetterFish, public BetterMouse{
public:
    explicit Monster(string name) : Animal(name, 1),  BetterMouse(name, 1), BetterBird(name, 1), BetterFish(name, 1){}
    Monster(Animal& animal) : Animal(animal.getName(), 1), BetterMouse(animal.getName(), 1), BetterBird(animal.getName(), 1), BetterFish(animal.getName(), 1) {};
    void attack(Animal& other) override{// override pure virtual method from Animal class
        other.setDaysLived(0);// set for attacked animal zero days of living -> it dies
    }
};
//create containers for animals with generic T type
template <typename T>
class Cage{
public:
    vector<unique_ptr<T>> container; // container of animals
    void sorting(){ // method for sorting
        sort(container.begin(), container.end(), [](unique_ptr<T>& first, unique_ptr<T>& second){ //lambda expression
            if (first -> getDaysLived() != second->getDaysLived()){ // firstly, sort be the days
                return first -> getDaysLived() < second -> getDaysLived();
            }
            return first -> getName() < second -> getName(); // otherwise, lexicographical sorting
        });
    }
    Cage() = default; // default contractor
};
// we cannot set fish into the cage
template<>
class Cage<Fish> {
public:
    Cage() = delete; // restrict to create such container
};
//create containers for animals with generic T type
template <typename T>
class Aquarium {
public:
    vector<unique_ptr<T>> container;// container of animals
    void sorting(){ // method for sorting
        sort(container.begin(), container.end(), [](unique_ptr<T>& first, unique_ptr<T>& second){ //lambda expression
            if (first -> getDaysLived() != second->getDaysLived()){ // firstly, sort be the days
                return first -> getDaysLived() < second -> getDaysLived();
            }
            return first -> getName() < second -> getName(); // otherwise, lexicographical sorting
        });
    }

    Aquarium() = default;// default contractor
};
// we cannot create such container
template<>
class Aquarium<Bird>{
public:
    Aquarium() = delete;// restrict to create such container
};
// container for freedom can accept any types of animal
template <typename A>
class Freedom{
public:
    vector<unique_ptr<A>> container; //container for animals
    void sorting(){ // method for sorting
        sort(container.begin(), container.end(), [](unique_ptr<A>& first, unique_ptr<A>& second){ //lambda expression
            if (first -> getDaysLived() != second->getDaysLived()){ // firstly, sort be the days
                return first -> getDaysLived() < second -> getDaysLived();
            }
            return first -> getName() < second -> getName(); // otherwise, lexicographical sorting
        });
    }
    Freedom() = default; // default contractor
};
// the main function
int main()
{
    //number of commands
    int CommandNumber;
    cin >> CommandNumber; // read number of commands
    cin.ignore();//skip line to read in particular commands
    Cage<Bird> cageBird; // cage for bird
    Cage<BetterBird> cageBetterBird; // cage for BetterBird
    Cage<Mouse> cageMouse; // cage for Mouse
    Cage<BetterMouse> cageBetterMouse; // cage for BetterMouse
    Aquarium<Fish> aquariumFish; //aquarium for fish
    Aquarium<BetterFish> aquariumBetterFish;//aquarium for BetterFish
    Aquarium<Mouse> aquariumMouse;//aquarium for Mouse
    Aquarium<BetterMouse> aquariumBetterMouse;//aquarium for BetterMouse
    Freedom<Animal> freedom; // container for animals
    for(int i = 0; i < CommandNumber; i++){ // read commands
        string command;// line of command
        getline(cin, command);// read live of command
        auto splitCommand= command | views::split(' '); // split line by space
        vector<string> newSplitCommand; // vector of parameters for command
        for (const auto& a : splitCommand){
            newSplitCommand.emplace_back(a.begin(), a.end()); // add parameters in vector
        }

        if (newSplitCommand[0] == "CREATE") { // if the command CREATE
            const string& animalType = newSplitCommand[1]; //animal type
            string animalName = newSplitCommand[2]; // animal name
            const string& container = newSplitCommand[4]; // container's name
            int days = stoi(newSplitCommand[5]); // apply stoi() to convert string to the integer -> number of days lived
            if (animalType == "B"){ // bird
                if (container == "Aquarium") continue; // we cannot add bird in aquarium
                unique_ptr<Bird> bird = make_unique<Bird>(animalName, days); // create the instance of the Bird class by unique ptr
                bird -> sayName(); // apply sayName()

                if (container == "Cage"){ // container is cage
                    cageBird.container.push_back(std::move(bird)); // add bird to the cage
                    cageBird.sorting(); // sort the container after adding
                } else if (container == "Freedom") {// container is Freedom
                    freedom.container.push_back(std::move(bird));// add bird to the freedom
                    freedom.sorting();// sort the container after adding
                }
            } else if (animalType == "M") {// mouse
                unique_ptr<Mouse> mice = make_unique<Mouse>(animalName, days);// create the instance of the Mouse class by unique ptr
                mice -> sayName(); // apply sayName()
                if (container == "Cage"){// container is cage
                    cageMouse.container.push_back(std::move(mice));// add Mouse to the cage
                    cageMouse.sorting();// sort the container after adding
                } else if (container == "Aquarium"){// container is Aquarium
                    aquariumMouse.container.push_back(std::move(mice));// add Mouse to the Aquarium
                    aquariumMouse.sorting();// sort the container after adding
                } else if (container == "Freedom") {// container is Freedom
                    freedom.container.push_back(std::move(mice));// add Mouse to the Freedom
                    freedom.sorting();// sort the container after adding
                }

            } else if (animalType == "F") {// fish
                if (container == "Cage") continue;// we cannot add fish in cage
                unique_ptr<Fish> fish = make_unique<Fish>(animalName, days);// create the instance of the Mouse class by unique ptr
                fish -> sayName();// apply sayName()
                if (container == "Aquarium") {// container is Aquarium
                    aquariumFish.container.push_back(std::move(fish));// add fish to the Aquarium
                    aquariumFish.sorting();// sort the container after adding
                } else if (container == "Freedom") {// container is Freedom
                    freedom.container.push_back(std::move(fish));// add fish to the Freedom
                    freedom.sorting();// sort the container after adding
                }
            } else if (animalType == "BF"){// Better fish
                unique_ptr<BetterFish> betterFish = make_unique<BetterFish>(animalName, days);// create the instance of the betterFish class by unique ptr
                betterFish -> sayName();// apply sayName()
                if (container == "Aquarium"){// container is Aquarium
                    aquariumBetterFish.container.push_back(std::move(betterFish));// add betterFish to the Aquarium
                    aquariumBetterFish.sorting();// sort the container after adding
                } else if (container == "Freedom") {// container is Freedom
                    freedom.container.push_back(std::move(betterFish));
                    freedom.sorting();// sort the container after adding
                }
            } else if (animalType == "BB"){
                unique_ptr<BetterBird> betterBird = make_unique<BetterBird>(animalName, days);
                betterBird -> sayName();// apply sayName()
                if (container == "Cage"){// container is cage
                    cageBetterBird.container.push_back(std::move(betterBird));// create the instance of the BetterBird class by unique ptr
                    cageBetterBird.sorting();// sort the container after adding
                } else if (container == "Freedom") {// container is Freedom
                    freedom.container.push_back(std::move(betterBird));// add BetterBird to the Freedom
                    freedom.sorting();// sort the container after adding
                }


            } else if (animalType == "BM"){
                unique_ptr<BetterMouse> betterMouse = make_unique<BetterMouse>(animalName, days);// create the instance of the betterMouse class by unique ptr
                betterMouse -> sayName();// apply sayName()
                if (container == "Aquarium"){// container is Aquarium
                    aquariumBetterMouse.container.push_back(std :: move(betterMouse));// add BetterMouse to the Aquarium
                    aquariumBetterMouse.sorting();// sort the container after adding
                } else if (container == "Cage"){// container is cage
                    cageBetterMouse.container.push_back(std :: move(betterMouse));// add BetterMouse to the Cage
                    cageBetterMouse.sorting();// sort the container after adding
                } else if (container == "Freedom"){// container is Freedom
                    freedom.container.push_back(std::move(betterMouse));// add BetterMouse to the Freedom
                    freedom.sorting();// sort the container after adding
                }
            }
        } else if (newSplitCommand[0] == "REMOVE_SUBSTANCE") { // apply_substance method
            const string& container = newSplitCommand[1]; // container

            if (container == "Freedom"){ // "Substance cannot be removed in freedom" should be printed
                cout << "Substance cannot be removed in freedom" << endl;
                continue;
            }
            const string& animalType = newSplitCommand[2]; // animal type
            int position = stoi(newSplitCommand[3]); // position of the animal
            if (animalType == "B") { // cannot remove substance from the normal animal
                cout << "Invalid substance removal" << endl;
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBird.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }

            } else if (animalType == "BB"){ // betterbird
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBetterBird.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                auto betterBird = std::move(cageBetterBird.container.at(position));// move the pointer from the container
                auto betterToNormal = make_unique<Bird>(*betterBird); // make a copy of the animal
                betterToNormal ->setDaysLived(betterToNormal -> getDaysLived() * 2);// update days
                cageBird.container.push_back(std:: move(betterToNormal)); // add animal into the new container
                cageBetterBird.container.erase(cageBetterBird.container.begin() + position); // delete the place from the old container
                cageBetterBird.sorting();// update container
                cageBird.sorting();// update container


            } else if (animalType == "M") {
                if (container == "Cage"){// cannot remove substance from the normal animal
                    cout << "Invalid substance removal" << endl;
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }

                } else if (container == "Aquarium"){// cannot remove substance from the normal animal
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    cout << "Invalid substance removal" << endl; // print the string
                }

            } else if (animalType == "BM"){ // better mouse
                if (container == "Aquarium"){// cannot remove substance from the normal animal
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumBetterMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Invalid substance removal" << endl;
                        continue;
                    }
                    auto betterMouse = std::move(aquariumBetterMouse.container.at(position));// move the pointer from the container
                    auto betterToNormal = make_unique<Mouse>(*betterMouse);// make a copy of the animal
                    betterToNormal ->setDaysLived(betterToNormal -> getDaysLived() * 2);// update days
                    aquariumMouse.container.push_back(std:: move(betterToNormal));// add animal into the new container
                    aquariumBetterMouse.container.erase(aquariumBetterMouse.container.begin() + position);// delete the place from the old container
                    aquariumBetterMouse.sorting();// update container
                    aquariumMouse.sorting();// update container

                } else if (container == "Cage"){ // container is Cage
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageBetterMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    auto betterMouse = std::move(cageBetterMouse.container.at(position));// move the pointer from the container
                    auto betterToNormal = make_unique<Mouse>(*betterMouse);// make a copy of the animal
                    betterToNormal ->setDaysLived(betterToNormal -> getDaysLived() * 2);// update days
                    cageMouse.container.push_back(std:: move(betterToNormal));// add animal into the new container
                    cageBetterMouse.container.erase(cageBetterMouse.container.begin() + position);// delete the place from the old container
                    cageBetterMouse.sorting();// update container
                    cageMouse.sorting();// update container
                }


            } else if (animalType == "F"){ // fish animal
                cout << "Invalid substance removal" << endl; // we cannot remove substance from normal fish
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumFish.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
            } else if (animalType == "BF"){ // better fish
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumBetterFish.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                auto betterFish = std::move(aquariumBetterFish.container.at(position));// move the pointer from the container
                auto betterToNormal = make_unique<Fish>(*betterFish);// make a copy of the animal
                betterToNormal ->setDaysLived(betterToNormal -> getDaysLived() * 2);// update days
                aquariumFish.container.push_back(std:: move(betterToNormal));// add animal into the new container
                aquariumBetterFish.container.erase(aquariumBetterFish.container.begin() + position);// delete the place from the old container
                aquariumBetterFish.sorting();// update container
                aquariumFish.sorting();// update container
            }

        } else if (newSplitCommand[0] == "APPLY_SUBSTANCE") { // apply substance method
            const string& container = newSplitCommand[1]; // container's name

            if (container == "Freedom"){ // we cannot apply substance into the freedom
                cout << "Substance cannot be applied in freedom" << endl;
                continue;
            }
            const string& animalType = newSplitCommand[2]; // animal type
            int position = stoi(newSplitCommand[3]); // animal position in container
            if (animalType == "B") {// bird
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBird.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                auto bird = std::move(cageBird.container.at(position));// move the pointer from the container
                auto betterBird = make_unique<BetterBird>(*bird);// make a copy of the animal
                betterBird->setDaysLived(ceil(double(betterBird -> getDaysLived()) / 2));//decrease number of days by 2 rounding up
                cageBetterBird.container.push_back(std::move(betterBird));// add animal into the new container
                cageBird.container.erase(cageBird.container.begin() + position);// delete the place from the old container
                cageBird.sorting();// update container
                cageBetterBird.sorting();// update container
            } else if (animalType == "BB"){//better bird
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBetterBird.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                auto betterBird = std::move(cageBetterBird.container.at(position));// move the pointer from the container
                auto monster = make_unique<Monster>(*betterBird);// make a copy of the animal
                freedom.container.push_back(std::move(monster));// add animal into the new container
                cageBetterBird.container.erase(cageBetterBird.container.begin() + position);// delete the place from the old container


                cageBetterBird.container.clear(); // now animal is monster -> kill all animals in the container
                freedom.sorting();// update container

            } else if (animalType == "M") {//mouse
                if (container == "Cage"){ // cage in container
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    auto mouse = std::move(cageMouse.container.at(position));// move the pointer from the container
                    auto betterMouse = make_unique<BetterMouse>(*mouse);// make a copy of the animal
                    betterMouse->setDaysLived(ceil(double(betterMouse -> getDaysLived()) / 2));//decrease number of days by 2 rounding up
                    cageBetterMouse.container.push_back(std::move(betterMouse));// add animal into the new container
                    cageMouse.container.erase(cageMouse.container.begin() + position);// delete the place from the old container
                    cageMouse.sorting();// update container

                    cageBetterMouse.sorting();// update container
                } else if (container == "Aquarium"){
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    auto mouse = std::move(aquariumMouse.container.at(position));// move the pointer from the container
                    auto betterMouse = make_unique<BetterMouse>(*mouse);// make a copy of the animal
                    betterMouse->setDaysLived(ceil(double(betterMouse -> getDaysLived()) / 2));//decrease number of days by 2 rounding up
                    aquariumBetterMouse.container.push_back(std::move(betterMouse));// add animal into the new container
                    aquariumMouse.container.erase(aquariumMouse.container.begin() + position);// delete the place from the old container
                    aquariumBetterMouse.sorting();// update container

                    aquariumMouse.sorting();// update container

                }

            }  else if (animalType == "BM"){// better Mouse
                if (container == "Aquarium"){ // container is aquarium
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumBetterMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    auto betterMouse = std::move(aquariumBetterMouse.container.at(position));// move the pointer from the container
                    auto monster = make_unique<Monster>(*betterMouse);// make a copy of the animal
                    freedom.container.push_back(std::move(monster));// add animal into the new container
                    aquariumBetterMouse.container.erase(aquariumBetterMouse.container.begin() + position);// delete the place from the old container

                    aquariumBetterMouse.container.clear();// now animal is monster -> kill all animals in the container
                    freedom.sorting();// update container

                } else if (container == "Cage"){ // cage is container
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageBetterMouse.container.at(position);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    auto betterMouse = std::move(cageBetterMouse.container.at(position));// move the pointer from the container
                    auto monster = make_unique<Monster>(*betterMouse);// make a copy of the animal
                    freedom.container.push_back(std::move(monster));// add animal into the new container
                    cageBetterMouse.container.erase(cageBetterMouse.container.begin() + position);// delete the place from the old container

                    cageBetterMouse.container.clear();// now animal is monster -> kill all animals in the container
                    freedom.sorting();// update container

                }


            }  else if (animalType == "F"){//animal is fish
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumFish.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                auto fish = std::move(aquariumFish.container.at(position));// move the pointer from the container
                auto betterFish = make_unique<BetterFish>(*fish);// make a copy of the animal
                betterFish->setDaysLived(ceil(double(betterFish -> getDaysLived()) / 2));//decrease number of days by 2 rounding up
                aquariumBetterFish.container.push_back(std::move(betterFish));// add animal into the new container
                aquariumFish.container.erase(aquariumFish.container.begin() + position);// delete the place from the old container
                aquariumBetterFish.sorting();// update container
                aquariumFish.sorting();// update container

            }else if (animalType == "BF"){// better fish type
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumBetterFish.container.at(position);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                auto betterFish = std::move(aquariumBetterFish.container.at(position));// move the pointer from the container
                auto monster = make_unique<Monster>(*betterFish);// make a copy of the animal
                freedom.container.push_back(std::move(monster));// add animal into the new container
                aquariumBetterFish.container.erase(aquariumBetterFish.container.begin() + position);// delete the place from the old container


                aquariumBetterFish.container.clear();// now animal is monster -> kill all animals in the container
                freedom.sorting();// update container
            }

        } else if (newSplitCommand[0] == "TALK"){// command TALK
            const string& container = newSplitCommand[1]; //container's name
            if (container == "Freedom"){ // if container is freedom
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    freedom.container.at(stoi(newSplitCommand[2]));
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                // call sayName() for animal from the freedom
                freedom.container.at(stoi(newSplitCommand[2])) -> sayName();
                continue; // continue for the new command
            }
            const string type = newSplitCommand[2]; // animal type

            int position1 = stoi(newSplitCommand[3]); // animal index in the container

            if (type == "B") { // bird
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBird.container.at(position1);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                cageBird.container.at(position1) -> sayName(); // sayName() for the animal
            } else if (type == "BB") { // betterBird
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBetterBird.container.at(position1);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                cageBetterBird.container.at(position1) -> sayName(); //call sayName() method

            } else if (type == "M") { //Mouse animal
                if (container == "Cage"){ // container is cage
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageMouse.container.at(position1);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    cageMouse.container.at(position1) -> sayName();//call sayName() method
                } else if (container == "Aquarium"){
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumMouse.container.at(position1);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    aquariumMouse.container.at(position1) -> sayName();//call sayName() method
                }

            } else if (type == "BM"){ // betterMouse animal
                if (container == "Aquarium"){ // container is aquarium
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumBetterMouse.container.at(position1);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }

                    aquariumBetterMouse.container.at(position1) -> sayName(); // call sayName() method
                } else if (container == "Cage"){ // for cage container
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageBetterMouse.container.at(position1);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    cageBetterMouse.container.at(position1) -> sayName(); // call sayName() method
                }


            } else if (type == "F"){ // animal if fish
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumFish.container.at(position1);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }

                aquariumFish.container.at(position1) -> sayName();// call sayName() method
            } else if (type == "BF"){ // betterFish
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumBetterFish.container.at(position1);

                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                aquariumBetterFish.container.at(position1) -> sayName(); // call sayName() method for the animal
            }

        } else if (newSplitCommand[0] == "ATTACK") { // ATTACK command
            const string&  container = newSplitCommand[1]; // name of container
            if (container == "Freedom"){ // for Freedom container
                cout << "Animals cannot attack in Freedom" << endl;
                continue;
            }
            const string&  type = newSplitCommand[2]; // type of animal
            int position1 = stoi(newSplitCommand[3]); // index for attacking animal
            int position2 = stoi(newSplitCommand[4]); // index for attacked animal

            if (type == "B") { //  Bird animal
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBird.container.at(position1);//check the valid index for both animals
                    cageBird.container.at(position2);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                cageBird.container.at(position1) -> attack(reinterpret_cast<Animal &>(cageBird.container.at(position2))); // apply attack() method for one animal
                cageBird.container.erase(cageBird.container.begin() + position2); // delete animal from the container
                cageBird.sorting(); // update container
                cout << "Bird is attacking" << endl; // print bird is attacking


            }  else if (type == "BB"){
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    cageBetterBird.container.at(position1);//check the valid index for both animals
                    cageBetterBird.container.at(position2);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                cageBetterBird.container.at(position1) -> attack(reinterpret_cast<Animal &>(cageBetterBird.container
                    .at(position2)));// apply attack() method for one animals
                cageBetterBird.container.erase(cageBetterBird.container.begin() + position2); // delete animal from the container
                cageBetterBird.sorting(); // update container
                cout << "BetterBird is attacking" << endl;

            } else if (type == "M") { // animal is mouse
                if (container == "Cage"){
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageMouse.container.at(position1);//check the valid index for both animals
                        cageMouse.container.at(position2);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }

                    cageMouse.container.at(position1) -> attack(reinterpret_cast<Animal &>(cageMouse.container
                        .at(position2)));// apply attack() method for one animal
                    cageMouse.container.erase(cageMouse.container.begin() + position2); // delete animal from the container because
                    cageMouse.sorting(); // update the animal
                    cout << "Mouse is attacking" << endl; // print "Mouse is attacking"
                } else if (container == "Aquarium"){ // container is aquarium
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumMouse.container.at(position1); //check the valid index for both animals
                        aquariumMouse.container.at(position2);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }
                    aquariumMouse.container.at(position1) -> attack(reinterpret_cast<Animal &>(aquariumMouse.container
                        .at(position2)));

                    aquariumMouse.container.erase(aquariumMouse.container.begin() + position2);
                    aquariumMouse.sorting();
                    cout << "Mouse is attacking" << endl;
                }

            }  else if (type == "BM"){
                if (container == "Aquarium"){
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        aquariumBetterMouse.container.at(position1);//check the valid index for both animals
                        aquariumBetterMouse.container.at(position2);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }

                    aquariumBetterMouse.container.at(position1) -> attack(reinterpret_cast<Animal &>(aquariumBetterMouse
                        .container.at(position2)));// apply attack() method for one animal
                    //aquariumBetterMouse.container.at(position2).reset();
                    aquariumBetterMouse.container.erase(aquariumBetterMouse.container.begin() + position2); // d

                    aquariumBetterMouse.sorting();
                    cout << "BetterMouse is attacking" << endl;
                } else if (container == "Cage"){
                    try {// check is the position with this animal exist, otherwise print "Animal not found"
                        cageBetterMouse.container.at(position1);//check the valid index for both animals
                        cageBetterMouse.container.at(position2);
                    } catch (const std::out_of_range& e){
                        cout << "Animal not found" << endl;
                        continue;
                    }

                    cageBetterMouse.container.at(position1) -> attack(reinterpret_cast<Animal &>(cageBetterMouse
                        .container.at(position2)));
                    cageBetterMouse.container.erase(cageBetterMouse.container.begin() + position2);
                    cageBetterMouse.sorting();
                    cout << "BetterMouse is attacking" << endl;
                }


            } else if (type == "F"){
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumFish.container.at(position1);//check the valid index for both animals
                    aquariumFish.container.at(position2);
                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }

                aquariumFish.container.at(position1) -> attack(reinterpret_cast<Animal &>(aquariumFish.container.at(position2)));
                aquariumFish.container.erase(aquariumFish.container.begin() + position2);// delete animal from the container
                aquariumFish.sorting();
                cout << "Fish is attacking" << endl;
            } else if (type == "BF"){
                try {// check is the position with this animal exist, otherwise print "Animal not found"
                    aquariumBetterFish.container.at(position1);//check the valid index for both animals
                    aquariumBetterFish.container.at(position2);

                } catch (const std::out_of_range& e){
                    cout << "Animal not found" << endl;
                    continue;
                }
                aquariumBetterFish.container.at(position1) -> attack(reinterpret_cast<Animal &>(aquariumBetterFish
                    .container.at(position2)));// apply attack() method for one animal
                aquariumBetterFish.container.erase(aquariumBetterFish.container.begin() + position2);// delete animal from the container
                aquariumBetterFish.sorting();// update container
                cout << "BetterFish is attacking" << endl;
            }
        } else if (newSplitCommand[0] == "PERIOD") {// method PERIOD
            for (int i = 0; i < cageBird.container.size();){ // add 1 day for animal in container
                int days = cageBird.container.at(i) -> getDaysLived(); // get number of days
                if (days + 1 > 10){ // if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", cageBird.container.at(i) -> getName().c_str());
                    cageBird.container.erase(cageBird.container.begin() + i); // delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    cageBird.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            cageBird.sorting();// update container
            for (int i = 0; i < cageBetterBird.container.size();){// add 1 day for animal in container
                int days = cageBetterBird.container.at(i) -> getDaysLived();// get number of days

                if (days + 1 > 10){ // if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", cageBetterBird.container.at(i) -> getName().c_str());
                    cageBetterBird.container.erase(cageBetterBird.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    cageBetterBird.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            cageBetterBird.sorting();// update container
            for (int i = 0; i < cageMouse.container.size();){// add 1 day for animal in container
                int days = cageMouse.container.at(i) -> getDaysLived();// get number of days
                if (days + 1 > 10){ // if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", cageMouse.container.at(i) -> getName().c_str());
                    cageMouse.container.erase(cageMouse.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    cageMouse.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            cageMouse.sorting();// update container
            for (int i = 0; i < cageBetterMouse.container.size();){// add 1 day for animal in container
                int days = cageBetterMouse.container.at(i) -> getDaysLived(); // get number of days
                if (days + 1 > 10){// if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", cageBetterMouse.container.at(i) -> getName().c_str());
                    cageBetterMouse.container.erase(cageBetterMouse.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    cageBetterMouse.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            cageBetterMouse.sorting();// update container
            for (int i = 0; i < aquariumFish.container.size();){// add 1 day for animal in container
                int days = aquariumFish.container.at(i) -> getDaysLived(); // get number of days
                if (days + 1 > 10){// if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", aquariumFish.container.at(i) -> getName().c_str());
                    aquariumFish.container.erase(aquariumFish.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    aquariumFish.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            aquariumFish.sorting();// update container

            for (int i = 0; i < aquariumBetterFish.container.size();){// add 1 day for animal in container
                int days = aquariumBetterFish.container.at(i) -> getDaysLived();// get number of days
                if (days + 1 > 10){// if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", aquariumBetterFish.container.at(i) -> getName().c_str());
                    aquariumBetterFish.container.erase(aquariumBetterFish.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    aquariumBetterFish.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            aquariumBetterFish.sorting();// update container
            for (int i = 0; i < aquariumMouse.container.size();){// add 1 day for animal in container
                int days = aquariumMouse.container.at(i) -> getDaysLived();// get number of days
                if (days + 1 > 10){// if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", aquariumMouse.container.at(i) -> getName().c_str());
                    aquariumMouse.container.erase(aquariumMouse.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    aquariumMouse.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            aquariumMouse.sorting();// update container
            for (int i = 0; i < aquariumBetterMouse.container.size();){// add 1 day for animal in container
                int days = aquariumBetterMouse.container.at(i) -> getDaysLived();// get number of days
                if (days + 1 > 10){// if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", aquariumBetterMouse.container.at(i) -> getName().c_str());
                    aquariumBetterMouse.container.erase(aquariumBetterMouse.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    aquariumBetterMouse.container.at(i) -> setDaysLived(days + 1);
                    ++i;// since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            aquariumBetterMouse.sorting();// update container
            for (int i = 0; i < freedom.container.size();){// add 1 day for animal in container
                int days = freedom.container.at(i) -> getDaysLived();// get number of days
                auto* monster = dynamic_cast<Monster*>(freedom.container[i].get());
                if (days + 1 > 10 || monster){// if animal has more than 10 days -> it unfortunately dies
                    printf("%s has died of old days\n", freedom.container.at(i) -> getName().c_str());
                    freedom.container.erase(freedom.container.begin() + i);// delete animal from the container
                    continue;
                } else {
                    // otherwise add one day for animal
                    freedom.container.at(i) -> setDaysLived(days + 1);
                    ++i; // since it delete animal from the current container we should mention that it required to go throw the entire container with n size
                }
            }
            freedom.sorting();// update container
        }
    }
    return 0;
}
