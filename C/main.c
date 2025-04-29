#include <stdio.h>
#include <string.h>


//here, a function is declared that raises the number to the required degree
int power(int num, int power) {
    int result = 1;
    while (power > 0) {
        result *= num;
        power--;
    }
    return(result);
}

// !!!all functions for the translation of number systems take the value of a string - a translatable number!!!

// binary() function converts a number from binary to decimal
//The ASCII table is used here
int binary(char binary_number[]) {

    int len = strlen(binary_number); // the length of the translated number
    int dop = 0; //the variable in which the value will be stored in the decimal system

    //creating a loop that goes through the indexes of the translated number

    for(int j = 0; j < len; j++) {
        int b = binary_number[j]; // the value of the character according to the ASCII table
        dop = dop + ((b - 48) * power(2,len - j - 1));
    }
    // the decimal value is returned here
    return dop;
}

// oct() function converts a number from octal to decimal
// The ASCII table is used here
int oct(char number[]) {

    int len = strlen(number); // the length of the translated number
    int dop = 0; //the variable in which the value will be stored in the decimal system

    //creating a loop that goes through the indexes of the translated number

    for(int j = 0; j < len; j++) {
        int b = number[j]; // the value of the character according to the ASCII table
        dop = dop + ((b - 48) * power(8,len - j - 1));
    }
    // the decimal value is returned here
    return dop;
}

// hex() function converts a number from hexadecimal to decimal
// The ASCII table is used here

int hex(char hex_number[]) {

    int len = strlen(hex_number); // the length of the translated number
    int dop = 0; //the variable in which the value will be stored in the decimal system

    //creating a loop that goes through the indexes of the translated number

    for(int j = 0; j < len; j++) {
        int b = hex_number[j]; // the value of the character according to the ASCII table
        if  (b < 58) // here you get the value of the digits in the ASCII table
            dop = dop + ((b - 48) * power(16,len - j - 1));
        else dop = dop + ((b - 55) * power(16,len - j - 1)); // here you get the value of the leters in the ASCII table
    }
    // the decimal value is returned here
    return dop;
}
// dex_to_dex() function converts a string of numbers from the decimal system to the integer value

int dex_to_dex(char deximal_number[]) {

    int len = strlen(deximal_number); // the length of the translated number
    int dop = 0; //the variable in which the value will be stored in the decimal system

    //creating a loop that goes through the indexes of the translated number

    for(int j = 0; j < len; j++) {
        int b = deximal_number[j]; // the value of the character according to the ASCII table
        dop = dop + ((b - 48) * power(10,len - j - 1));

    }
    // the decimal value is returned here
    return dop;
}
// !!! The following checker() functions check whether a number is suitable for a certain number system !!!

// here we declare a function that checks for compliance with the decimal number system

int dex_checker(char number[]) {
    //creating a loop that checks whether the character in the ASCII table corresponds to the decimal number system
    // if the number is correct, the function returns 1, otherwise 0
    for (int i = 0; i < strlen(number); i++) {
        int b = number[i]; // the value of the character according to the ASCII table
        if (b <= 47  ||  b >= 58)
            return 0;
    }
    return 1;
}
// here we declare a function that checks for compliance with the binary number system

int binary_checker(char number[]) {
    //creating a loop that checks whether the character in the ASCII table corresponds to the binary number system
    // if the number is correct, the function returns 1, otherwise 0
    for (int i = 0; i < strlen(number); i++) {
        int b = number[i]; // the value of the character according to the ASCII table
        if (b != 48 && b != 49)
            return 0;
    }
    return 1;

}
// here we declare a function that checks for compliance with the octal number system

int oct_checker(char number[]) {
    //creating a loop that checks whether the character in the ASCII table corresponds to the octal number system
    // if the number is correct, the function returns 1, otherwise 0
    for (int i = 0; i < strlen(number); i++) {
        int b = number[i]; // the value of the character according to the ASCII table
        if (b <= 47 || b >= 56)
            return 0;
    }
    return 1;
}
// here we declare a function that checks for compliance with the octal number system
int hex_checker(char number[]) {
    //creating a loop that checks whether the character in the ASCII table corresponds to the octal number system
    // if the number is correct, the function returns 1, otherwise 0
    for (int i = 0; i < strlen(number); i++) {
        int b = number[i]; // the value of the character according to the ASCII table
        if ((b > 47 &&  b < 58) || (b > 64 && b < 71)) {
            continue;
        }
        else return 0;
    }
    return 1;

}
// !!! the main function declares below !!!
int main()
{
    FILE* file_input = fopen("input.txt", "r"); // here the file is being read for reading
    FILE* file_output = fopen("output.txt", "w"); // here the file if being read for writing
    // checking if the file exists
    if (file_input == NULL) {
        fprintf(file_output, "Invalid inputs\n");
        return 0;
    }

    int N; // the variable N stores the value of the number of numbers in the following lines
    fscanf(file_input, "%d", &N); //reading N from the file

    char array_of_numbers[N][50]; // declaring an array in which numbers in different number systems will be written
    int number_system[N]; // declaring an array number_system[N] of type int, in which different number systems will be written
    int dex_array[N]; // we declare an array dex_array[N] in which the numbers already converted to the decimal system will be stored
    // n dex checker

    // reading N numbers from the file
    for (int i = 0; i < N; i++) {
        fscanf(file_input, "%s", array_of_numbers[i]);
    }
    // we read N number systems from the file
    for (int i = 0; i < N; i++) {
        fscanf(file_input, "%d", &number_system[i]);
    }


    // checkers
    // here checking the range of possile value of N-number: 1 <= N <= 40
    if (N <= 0 || N > 40) {
        fprintf(file_output, "Invalid inputs\n");
        return 0;
    }
    // Checking the possible values of number systems. It should contain only 2, 8, 10, 16
    for  (int i = 0; i < N; i++) {
        if (number_system[i] == 2 || number_system[i] == 8  || number_system[i] == 10 || number_system[i] == 16) {
            continue;
        }
        else {
            fprintf(file_output, "Invalid inputs\n");
            return 0;
        }
    }
    // The loop below reads the number system and use checkers.
    for (int i = 0; i < N; i++) {
        if (number_system[i] == 2) { // for binary number
            if (strlen(array_of_numbers[i]) <= 6) { // if the length of number more than 6, return 0 and it prints the error
                if (binary_checker(array_of_numbers[i]) == 0 ) {
                    fprintf(file_output, "Invalid inputs\n");
                    return 0;
                }
                else dex_array[i] = binary(array_of_numbers[i]);// translating from binary to deximal
            }
            else {
                fprintf(file_output, "Invalid inputs\n");
            }

        }
        if (number_system[i] == 8) { // for octal number
            if (strlen(array_of_numbers[i]) <= 6) {
                if (oct_checker(array_of_numbers[i]) == 0 ) {// if the length of number more than 6, return 0 and it prints the error
                    fprintf(file_output, "Invalid inputs\n");
                    return 0;
                }
                else dex_array[i] = oct(array_of_numbers[i]); // translating from octal to deximal
            }
            else {
                fprintf(file_output, "Invalid inputs\n");
            }

        }
        if (number_system[i] == 10) { // for deximal number
            if (strlen(array_of_numbers[i]) <= 6) {
                if (dex_checker(array_of_numbers[i]) == 0 ) { // if the length of number more than 6, return 0 and it prints the error
                    fprintf(file_output, "Invalid inputs\n");
                    return 0;
                }
                else dex_array[i] = dex_to_dex(array_of_numbers[i]); // translating from deсimal to deсimal
            }
            else {
                fprintf(file_output, "Invalid inputs\n");
            }

        }
        if (number_system[i] == 16) { // for hexideximal number
            if (strlen(array_of_numbers[i]) <= 6) {// if the length of number more than 6, return 0 and it prints the error
                if (hex_checker(array_of_numbers[i]) == 0 ) {
                    fprintf(file_output, "Invalid inputs\n");
                    return 0;
                }
                else dex_array[i] = hex(array_of_numbers[i]); // translating from hexadecimal to decimal
            }
            else {
                fprintf(file_output, "Invalid inputs\n");
            }

        }
    }

    int ANSWER = 0; // the answer of task
    for (int i = 0; i < N; i++) {
        if (i % 2 == 0) {
            ANSWER += dex_array[i] - 10; // if it is even index, the element of array summed up to ANSWER with -10
        }
        else ANSWER += dex_array[i] + 10; // if it  off index, the element of array summed up to ANSWER with +10
    }
    fprintf(file_output, "%d\n", ANSWER); // writing the response to a file
    fclose(file_input); // closed file
    return 0;
}
