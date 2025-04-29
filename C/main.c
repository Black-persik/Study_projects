#include <stdio.h>
#include <string.h>
// struct for students
struct student {
    int student_id;
    char name[100];
    char faculty[100];
};
// enum for types of exams
enum examtype {
    WRITTEN,
    DIGITAL
};
//union of info about exams
union examinfo {
    char software[30];
    int duration;

};
// struct of exams
struct exam {
    int exam_id;
    enum examtype exam_type;
    union examinfo exam_info;
};
// struct of exam's grades
struct ExamGrade {
    int exam_id;
    int student_id;
    int grade;

};
// function that transform string of exam type from str to enum
int from_string_to_enum(char x[]) {
    if (strcmp(x, "WRITTEN") == 0) {
        return WRITTEN;
    }
    if (strcmp(x, "DIGITAL") == 0) {
        return DIGITAL;
    }
    return 12;


}
// array of structs of students
struct student student[1000];
// count of students
int count_of_students = 0;

// array of structs of exams
struct exam exam[1000];
// count of exams
int count_of_exams = 0;

// array of structs of exams
struct ExamGrade exam_grade[1000];
// count of grades
int count_of_exam_grades = 0;

// !!! All following functions contain fiLe for output, except ADD_EXAM and UPDATE_EXAM that contain also file for input !!!

// !!! When the checking found an error, the function print the information about this error, and it is no longer being executed

//function ADD_student
void ADD_STUDENT(int student_id, char name[], char faculty[], FILE* file_output) { //contain student id, name of student and faculty
    // checkers

    // for already exist id
    for (int i = 0; i < count_of_students; i++) {
        if (student[i].student_id == student_id) {

            fprintf(file_output, "Student: %d already exists\n",student_id);
            return;

        }
    }
    // for id
     if (student_id <= 0 || student_id >= 1000) {
        fprintf(file_output, "Invalid student id\n");
        return;
    }
    // for name

    if (strlen(name) <= 1 || strlen(name) >= 20) {
        fprintf(file_output, "Invalid name\n");
        return;
    }

    for (int i = 0; i < strlen(name); i ++ ) {
        if ((name[i] < 'a' || name[i] > 'z') && (name[i] < 'A' || name[i] > 'Z')) {
            fprintf(file_output, "Invalid name\n");
            return;
        }
    }

    // for faculty

    if (strlen(faculty) <= 4 || strlen(faculty) >= 30) {
        fprintf(file_output, "Invalid faculty\n");
        return;
    }

    for (int i = 0; i < strlen(faculty); i ++ ) {
        if ((faculty[i] < 'a' || faculty[i] > 'z') && (faculty[i] < 'A' || faculty[i] > 'Z')) {
            fprintf(file_output, "Invalid faculty\n");
            return;
        }
    }



    // if values are valid, we can add information about student if struct student
    student[count_of_students].student_id = student_id;
    strcpy(student[count_of_students].name, name);
    strcpy(student[count_of_students].faculty, faculty);
    fprintf(file_output, "Student: %d added\n", student_id);
    //when the information is added, the count of students is increased by 1
    count_of_students++;
};
//the function ADD_EXAM is added information about exam in struct Exam
void ADD_EXAM(int exam_id, char exam_type[], FILE* file_output, FILE* file_input) {
    // checkers on valid inputs

    union examinfo exam_info; // here union examinfo is declared
    // check if exam already exists
    for (int i = 0; i < count_of_exams; i ++ ) {
        if (exam[i].exam_id == exam_id) {
            fprintf(file_output, "Exam: %d already exists\n",exam_id);
            return ;
        }
    }
    // check the validity of exam_id
    if (exam_id <= 0 || exam_id >= 500) {
        fprintf(file_output, "Invalid exam id\n");
        return;
    }
    // check the validity of exam_type
    if (from_string_to_enum(exam_type) != WRITTEN && from_string_to_enum(exam_type) != DIGITAL) {
        fprintf(file_output, "Invalid exam type\n");
        return;
    }


    // As we have union, we check firstly the type of exams because of duration is integer value and software is string value
    // So our choice can be either WRITTEN or DIGITAL
    if (from_string_to_enum(exam_type) == WRITTEN) {
        fscanf(file_input, "%d", &exam_info.duration);
        //check is the duration is valid
        if (exam_info.duration < 40 || exam_info.duration > 180) {
            fprintf(file_output, "Invalid duration\n");
            return ;
        }

        // add information about duration
        exam[count_of_exams].exam_info.duration = exam_info.duration;


    }
    if (from_string_to_enum(exam_type) == DIGITAL) {
        fscanf(file_input, "%s", exam_info.software);
        //checking is the software validity
        if (strlen(exam_info.software) <= 2 || strlen(exam_info.software) >= 20) {
            fprintf(file_output, "Invalid software\n");
            return;
        }
        for (int i = 0; i < strlen(exam_info.software); i++ ) {
            if ((exam_info.software[i] < 'a' || exam_info.software[i] > 'z') && (exam_info.software[i] < 'A' || exam_info.software[i] > 'Z')) {
                fprintf(file_output, "Invalid software\n");
                return;
            }
        }
    }
    // add information of exam
    exam[count_of_exams].exam_id = exam_id;
    exam[count_of_exams].exam_type = from_string_to_enum(exam_type);
    if (from_string_to_enum(exam_type) == WRITTEN) {
        fprintf(file_output, "Exam: %d added\n", exam_id);
        //when the information is added, the count of exams is increased by 1
        count_of_exams++;
        return;

    }
    else if (from_string_to_enum(exam_type) == DIGITAL) {
        strcpy(exam[count_of_exams].exam_info.software, exam_info.software);
        fprintf(file_output, "Exam: %d added\n", exam_id);
        //when the information is added, the count of exams is increased by 1
        count_of_exams++;
    }
};
// function ADD_GRADE add information of the grade using student and exam id

void ADD_GRADE(int exam_id, int student_id, int grade, FILE* file_output) {
    // checking of existing of exams, using flag
    int flag_exam = 0;
    for (int i = 0; i < count_of_exams; i ++ ) {
        if (exam[i].exam_id == exam_id) {
            flag_exam += 1;
            break;
        }
    }
    if (flag_exam == 0) {
        fprintf(file_output, "Exam not found\n");
        return;
    }
    // checking the validity of exam_id
    if (exam_id <= 0 || exam_id >= 500) {
        fprintf(file_output, "Invalid exam id\n");
        return;
    }
    // checking of existing of students, using flag
    int flag_student = 0;
    for(int i  = 0; i < count_of_students; i ++) {
        if (student[i].student_id == student_id) {
            flag_student += 1;
            break;
        }
    }
    if (flag_student == 0) {
        fprintf(file_output ,"Student not found\n");
        return;
    }
    // checking the validity of student id
    if (student_id <= 0 || student_id >= 1000) {
        fprintf(file_output, "Invalid student id\n");
        return;
    }
    // checking the validity of grades
    if(grade < 0 || grade > 100) {
        fprintf(file_output, "Invalid grade\n");
        return;
    }


    // add information of grade
    exam_grade[count_of_exam_grades].exam_id = exam_id;
    exam_grade[count_of_exam_grades].student_id = student_id;
    exam_grade[count_of_exam_grades].grade = grade;
    fprintf(file_output, "Grade %d added for the student: %d\n", grade, student_id);
    //when the information is added, the count of grades is increased by 1
    count_of_exam_grades++;

};
// function that search certain student in array of struct of students
void SEARCH_STUDENT(int student_id, FILE* file_output) {
    // checking of existing of students, using flag
    int flag_student = 0;
    for(int i  = 0; i < count_of_students; i ++) {
        if (student[i].student_id == student_id) {
            flag_student += 1;
            break;
        }
    }
    if (flag_student == 0) {
        fprintf(file_output ,"Student not found\n");
        return;
    }
    //checking the validity of student id
    if (student_id <= 0 || student_id >= 1000) {
        fprintf(file_output, "Invalid student id\n");
        return;
    }
    // output of the found values
    for (int i = 0; i < count_of_students; i++) {
        if (student[i].student_id == student_id) {
            fprintf(file_output, "ID: %d, Name: %s, Faculty: %s\n", student[i].student_id, student[i].name, student[i].faculty );
            return;
        }
    }


};
// the function finds the information for certain student of grades
void SEARCH_GRADE(int exam_id, int student_id, FILE* file_output) {
    // checking of existing of exams, using flag
    int flag_exam = 0;
    for (int i = 0; i < count_of_exams; i ++ ) {
        if (exam[i].exam_id == exam_id) {
            flag_exam += 1;
            break;
        }
    }
    if (flag_exam == 0) {
        fprintf(file_output, "Exam not found\n");
        return;
    }
    //checking the validity of exam id
    if (exam_id <= 0 || exam_id >= 500) {
        fprintf(file_output, "Invalid exam id\n");
        return;
    }
    // checking of existing of students, using flag
    int flag_student = 0;
    for(int i  = 0; i < count_of_students; i ++) {
        if (student[i].student_id == student_id) {
            flag_student += 1;
            break;
        }
    }
    if (flag_student == 0) {
        fprintf(file_output ,"Student not found\n");
        return;
    }
    //checking the validity of student id
    if (student_id <= 0 || student_id >= 1000) {
        fprintf(file_output, "Invalid student id\n");
        return;
    }

    // if information is correct, the function find the grade and information
    for(int i = 0; i < count_of_exam_grades; i++) {
        //find the exam_id and student_id in array of structs
        if (exam_grade[i].exam_id == exam_id && exam_grade[i].student_id == student_id) {
            for (int j = 0; j < count_of_students; j++) {
                for(int k = 0; k < count_of_exams; k++) {
                    if (student[j].student_id == student_id && exam[k].exam_id == exam_id) {
                        // if exam type is written
                        if(exam[k].exam_type == WRITTEN) {
                            fprintf(file_output, "Exam: %d, Student: %d, Name: %s, Grade: %d, Type: WRITTEN, Info: %d\n", exam_id, student_id, student[j].name, exam_grade[i].grade, exam[k].exam_info.duration);
                            return;
                        }
                        // if exam type is digital
                        if (exam[k].exam_type == DIGITAL) {
                            fprintf(file_output, "Exam: %d, Student: %d, Name: %s, Grade: %d, Type: DIGITAL, Info: %s\n", exam_id, student_id, student[j].name, exam_grade[i].grade, exam[k].exam_info.software);
                            return;
                        }

                    }

                }
            }

        }
    }


};
// function update information about the exam
void UPDATE_EXAM(int exam_id, char new_exam_type[], FILE* file_output, FILE* file_input) {
    // declare the union of exam info
    union examinfo exam_info;
    // checking the type of exam
    if (from_string_to_enum(new_exam_type) != WRITTEN && from_string_to_enum(new_exam_type) != DIGITAL) {
        fprintf(file_output, "Invalid exam type\n");
        return;
    }
    // if type is DIGITAL
    if (from_string_to_enum(new_exam_type) == DIGITAL) {
        fscanf(file_input, "%s", exam_info.software); // reads information as a string
        //checking the validity of software
        if (strlen(exam_info.software) <= 2 || strlen(exam_info.software) >= 20) {
            fprintf(file_output, "Invalid software\n");
            return;
        }

        for (int i = 0; i < strlen(exam_info.software); i ++ ) {
            if ((exam_info.software[i] < 'a' || exam_info.software[i] > 'z') && (exam_info.software[i] < 'A' || exam_info.software[i] > 'Z')) {
                fprintf(file_output, "Invalid software\n");
                return;
            }
        }
    }
    // if exam is WRITTEN
    else if (from_string_to_enum(new_exam_type) == WRITTEN) {
        fscanf(file_input, "%d", &exam_info.duration); // reads as a string
        if (exam_info.duration < 40 || exam_info.duration > 180) {
            fprintf(file_output, "Invalid duration\n");
            return;
        }
    }

    // update information about exam
    for(int i = 0; i < count_of_exams; i++) {
        if (exam[i].exam_id == exam_id) {
            exam[i].exam_type = from_string_to_enum(new_exam_type);
            // if it is written
            if (exam[i].exam_type == WRITTEN) {
                fprintf(file_output, "Exam: %d updated\n", exam_id);
                return;
            }
            // if it is digital
            if (exam[i].exam_type == DIGITAL) {
                strcpy(exam[i].exam_info.software, exam_info.software);
                fprintf(file_output, "Exam: %d updated\n", exam_id);
                return;
            }

        }

    }


};
// function that update information about the grade for certain student
void UPDATE_GRADE(int exam_id, int student_id, int new_grade,FILE* file_output) {
    // checking the validity of grades
    if(new_grade < 0 || new_grade > 100) {
        fprintf(file_output, "Invalid grade\n");
        return;
    }
    // update information for new grade
    for(int i = 0; i < count_of_exam_grades; i++) {
        if(exam_grade[i].student_id == student_id && exam_grade[i].exam_id == exam_id) {
            exam_grade[i].grade = new_grade;
            fprintf(file_output, "Grade %d updated for the student: %d\n", new_grade, student_id);
            return;
        }
    }




};

// function delete the information of student and also his grades
void DELETE_STUDENT(int student_id, FILE* file_output) {

    // delete information about  student
    for(int i = 0; i < count_of_students; i++) {
        if(student[i].student_id == student_id) {
            for(int j = i; j < count_of_students - 1 ; j++ ) {
                student[j] = student[j + 1];
            }
            //reducing the number of students
            count_of_students--;

        }

    }
    // delete information of student's grades
    if (count_of_exam_grades > 0) {
        for(int i = 0; i < count_of_exam_grades; i++) {
            if(exam_grade[i].student_id == student_id) {
                for(int j = i; j < count_of_exam_grades - 1 ; j++ ) {
                    exam_grade[j] = exam_grade[j + 1];
                }
                //reducing the number of grades
                count_of_exam_grades--;
            }
        }
    }

    fprintf(file_output, "Student: %d deleted\n", student_id);



};
//function lists all students as the same format in SEARCH_STUDENT
void LIST_ALL_STUDENTS(FILE* file_output) {
    for(int i = 0; i < count_of_students; i++) {
        fprintf(file_output,"ID: %d, Name: %s, Faculty: %s\n", student[i].student_id, student[i].name, student[i].faculty);
    }
};


// the main function
int main() {
    // file_input and file_output for input and output data
    FILE* file_input = fopen("input.txt", "r");
    FILE* file_output = fopen("output.txt", "w");
    //here the loop while will work until the command END is detected
    while (1) {
        char command[30]; // char command[30] contains the name of functions
        fscanf(file_input, "%s", command);
        // if command ADD_STUDENT
        if (strcmp(command, "ADD_STUDENT") == 0) {
            // read information from input file
            int student_id;
            char name[100];
            char faculty[100];
            fscanf(file_input, "%d", &student_id);
            fscanf(file_input, "%s", name);
            fscanf(file_input, "%s", faculty);
            // call the function ADD_STUDENT
            ADD_STUDENT(student_id, name, faculty, file_output);
        }
        else if (strcmp(command, "ADD_EXAM") == 0) {
            // read information from input file
            int exam_id;
            char exam_type[300];
            fscanf(file_input, "%d", &exam_id);
            fscanf(file_input, "%s", exam_type);
            // call the function ADD_EXAM
            ADD_EXAM(exam_id, exam_type, file_output, file_input);
        }
        else if (strcmp(command, "ADD_GRADE") == 0) {
            // read information from input file
            int exam_id;
            int student_id;
            int grade;
            fscanf(file_input, "%d", &exam_id);
            fscanf(file_input, "%d", &student_id);
            fscanf(file_input, "%d", &grade);
            // call the function ADD_GRADE
            ADD_GRADE(exam_id, student_id, grade, file_output);

        }
        else if (strcmp(command, "SEARCH_STUDENT") == 0) {
            // read information from input file
            int student_id;
            fscanf(file_input, "%d", &student_id);
            // call the function SEARCH_STUDENT
            SEARCH_STUDENT(student_id, file_output);
        }
        else if (strcmp(command, "SEARCH_GRADE") == 0) {
            // read information from input file
            int exam_id;
            int student_id;
            fscanf(file_input, "%d", &exam_id);
            fscanf(file_input, "%d", &student_id);
            // call the function SEARCH_GARDE
            SEARCH_GRADE(exam_id, student_id, file_output);
        }

        else if (strcmp(command, "UPDATE_EXAM") == 0) {
            // read information from input file
            int exam_id;
            char new_exam_type[300];
            fscanf(file_input, "%d", &exam_id);
            fscanf(file_input, "%s", new_exam_type);
            // call the function UPDATE_EXAM
            UPDATE_EXAM(exam_id, new_exam_type,file_output, file_input);

        }

        else if(strcmp(command, "UPDATE_GRADE") == 0) {
            // read information from input file
            int exam_id;
            int student_id;
            int new_grade;
            fscanf(file_input, "%d", &exam_id);
            fscanf(file_input, "%d", &student_id);
            fscanf(file_input, "%d", &new_grade);
            // call the UPDATE_GRADE function
            UPDATE_GRADE(exam_id, student_id, new_grade, file_output);
        }
        else if (strcmp(command, "DELETE_STUDENT") == 0) {
            // read information from input file
            int student_id;
            fscanf(file_input, "%d", &student_id);
            // call the function DELETE_STUDENT
            DELETE_STUDENT(student_id, file_output);
        }
        else if (strcmp(command, "LIST_ALL_STUDENTS") == 0) {
            // call the function LIST_ALL_STUDENTS
            LIST_ALL_STUDENTS(file_output);
        }

        else if (strcmp(command, "END") == 0) {
            // break the loop in this case
            break;
        }


    }
    // close file for input and output
    fclose(file_input);
    fclose(file_output);
    return 0;

}
