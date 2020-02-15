// Lab4.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <stdlib.h>
#include <mutex>
#include <condition_variable>
#include <tuple>
#include <queue>
#include <thread>
#include <chrono>

using namespace std;

#define THREADS_1 10
#define THREADS_2 7

int a[100][100], b[100][100], c[100][100], res[100][100];
int dim;
mutex mtx;
condition_variable cv;
queue<tuple<int, int, int>> q;
bool finished;


void printMatrix(int matrix[100][100]) {
	for (int i = 0; i < dim; i++) {
		for (int j = 0; j < dim; j++) {
			std::cout << matrix[i][j] << " ";
		}
		std::cout << "\n";
	}
	std::cout << "\n";
}

void generateMatrix(int matrix[100][100]) {
	for (int i = 0; i < dim; i++) {
		for (int j = 0; j < dim; j++) {
			matrix[i][j] = rand() % 100;
		}
	}
}

inline void producer(int current, int threads) {
	for (int i = current; i < dim; i = i + threads) {
		for (int j = 0; j < dim; j++) {
			int p = 0;

			for (int k = 0; k < dim; k++) {
				p = p + a[i][k] * b[j][k];
			}

			{
				lock_guard<mutex> lock(mtx);
				q.push(make_tuple(i, j, p));
			}

			cv.notify_all();
		}
	}
}

inline void consumer() {
	while (true) {
		unique_lock<mutex> lock(mtx);
		cv.wait(lock, [] {return finished || !q.empty(); });

		if (finished)
			break;

		tuple<int, int, int> element = q.front();
		q.pop();
		int i = get<0>(element);
		int j = get<1>(element);
		int x = get<2>(element);

		for (int k = 0; k < dim; k++) {
			res[i][k] = res[i][k] + x * c[j][k];
		}
	}
}

int main()
{
	vector<thread> producers, consumers;
	std::cout << "Enter dimension: ";
	std::cin >> dim;

	generateMatrix(a);
	printMatrix(a);
	cout << endl;

	generateMatrix(b);
	printMatrix(b);
	cout << endl;

	generateMatrix(c);
	printMatrix(c);
	cout << endl;

	int nr_elems = dim * dim;
	int elems_in_thread = nr_elems / THREADS_1 + 1;
	int i = 0, j = 0;

	chrono::steady_clock::time_point start = chrono::steady_clock::now();

	for (int i = 0; i < THREADS_1; i++) {
		producers.emplace_back(producer, i, THREADS_1);
	}

	for (int i = 0; i < THREADS_2; i++) {
		consumers.emplace_back(consumer);
	}

	for (auto & producer : producers) {
		producer.join();
	}

	{
		lock_guard<mutex> lock(mtx);
		finished = true;
	}

	cv.notify_all();

	for (auto & consumer : consumers) {
		consumer.join();
	}

	printMatrix(res);

	chrono::steady_clock::time_point end = chrono::steady_clock::now();
	chrono::steady_clock::duration duration = end - start;

	cout << "Duration: " << chrono::duration <double, milli>(duration).count() << endl;
}

