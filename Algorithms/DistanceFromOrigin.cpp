//Ivan Vasilev
#include <iostream>
#include <vector>
#include <cmath>
#include <list>
#include <iomanip>
using namespace std;
template<typename T>
vector<T> IvanVasilev_merge_srt(const vector<T>& left, const vector<T>& right) {
    vector<T> sorted_list;
    int i = 0, j = 0;
    while (i < left.size() && j < right.size()) {
        if (left[i][0] <= right[j][0]) {
            sorted_list.push_back(left[i]);
            i++;
        } else {
            sorted_list.push_back(right[j]);
            j++;
        }
    }
    while (i < left.size()) {
        sorted_list.push_back(left[i]);
        i++;
    }
    while (j < right.size()) {
        sorted_list.push_back(right[j]);
        j++;
    }
    return sorted_list;
}
template<typename T>
vector<T> merge_sort(const vector<T>& array) {
    if (array.size() <= 1) {
        return array;
    }
    int middle = array.size() / 2;
    vector<T> left(array.begin(), array.begin() + middle);
    vector<T> right(array.begin() + middle, array.end());
    left = merge_sort(left);
    right = merge_sort(right);
    return IvanVasilev_merge_srt(left, right);
}
template<typename T>
void IvanVasilev_bucket_srt(vector<vector<T>>& array, int n) {
    vector<list<vector<T>>> answer(n);
    for (int i = 0; i < n; i++) {
        double d = array[i][0];
        int index = d * n;
        if (index >= n) {
            index = n - 1;
        }
        answer[index].push_back(array[i]);
    }
    for (int i = 0; i < n; i++) {
        vector<vector<T>> bucket(answer[i].begin(), answer[i].end());
        bucket = merge_sort(bucket);
        answer[i].clear();
        for (auto& item : bucket) {
            answer[i].push_back(item);
        }
    }
    int index = 0;
    for (int i = 0; i < n; i++) {
        for (auto& value : answer[i]) {
            array[index++] = value;
        }
    }
}

int main() {
    vector<vector<double>> array;
    int numberOfPairs;
    cin >> numberOfPairs;
    for (int i = 0; i < numberOfPairs; i++) {
        double x, y;
        cin >> x >> y;
        double d = sqrt(x * x + y * y);
        array.push_back({d, x, y});
    }
    IvanVasilev_bucket_srt(array, numberOfPairs);
    cout << fixed << setprecision(4);
    for (const auto& pair : array) {
        cout << pair[1] << " " << pair[2] << endl;
    }
    return 0;
}
