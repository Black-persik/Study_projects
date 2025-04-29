//IvanVasilev 
#include <iostream>
#include <vector>
#include <algorithm>
#include <utility>
using namespace std;

template <typename T>
vector<pair<T, vector<size_t>>> IvanVasilev_counting_srt(const vector<T>& array) {

    T min_val = *min_element(array.begin(), array.end());
    T max_val = *max_element(array.begin(), array.end());

    vector<vector<size_t>> count(max_val - min_val + 1);
    for (size_t i = 0; i < array.size(); ++i) {
        count[array[i] - min_val].push_back(i);
    }

    vector<pair<T, vector<size_t>>> result;
    result.reserve(count.size());
    for (T i = min_val; i <= max_val; ++i) {
        if (!count[i - min_val].empty()) {
            result.emplace_back(i, move(count[i - min_val]));
        }
    }
    return result;
}

template <typename T>
void CustomSwap(pair<T, vector<size_t>>& a, pair<T, vector<size_t>>& b) {
    swap(a, b);
}

template <typename T>
int partition(vector<pair<T, vector<size_t>>>& array, int low, int high) {
    auto& pivot = array[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
        if (array[j].second.size() < pivot.second.size() ||
            (array[j].second.size() == pivot.second.size() && array[j].first < pivot.first)) {
            CustomSwap(array[++i], array[j]);
        }
    }
    CustomSwap(array[i + 1], array[high]);
    return i + 1;
}

template <typename T>
void quickSort(vector<pair<T, vector<size_t>>>& array, int low, int high) {
    if (low < high) {
        int pi = partition(array, low, high);
        quickSort(array, low, pi - 1);
        quickSort(array, pi + 1, high);
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    size_t size;
    cin >> size;
    vector<int> array;
    array.reserve(size);
    for (size_t i = 0; i < size; ++i) {
        int element;
        cin >> element;
        array.push_back(element);
    }

    auto sorted = IvanVasilev_counting_srt(array);
    quickSort(sorted, 0, sorted.size() - 1);

    for (const auto& pair : sorted) {
        for (const auto& index : pair.second) {
            cout << pair.first << " " << index << "\n";
        }
    }
    return 0;
}
