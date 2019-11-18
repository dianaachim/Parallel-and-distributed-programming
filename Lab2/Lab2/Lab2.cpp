// Lab2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <iostream>
#include <vector>
#include <thread>
#include <ctime>

using namespace std;

void addMatrices(int a[100][100], int b[100][100], int sum[100][100], int dim, int i, int j) {
	
	sum[i][j] = a[i][j] + b[i][j];
}

void multiplyMatrices(int a[100][100], int b[100][100], int rez[100][100], int dim, int i, int j) {
	
	rez[i][j] = 0;
	for (int k = 0; k < dim; k++) {
		rez[i][j] += a[i][k] * b[k][j];
	}
}

void printMatrix(int a[100][100], int dim) {
	for (int i = 0; i < dim; i++) {
		for (int j = 0; j < dim; j++) {
			std::cout << a[i][j] << " ";
		}
		std::cout << "\n";
	}
	std::cout << "\n";
}

void runThreads(std::vector<std::thread*> threads, int a[100][100], int b[100][100], int res[100][100], int dim, int nr_threads) {
	int nr_elems = dim * dim;
	int elems_in_thread = nr_elems / nr_threads + 1;
	int i = 0, j = 0;
	

	for (int l = 0; l < nr_threads; l++) {
		for (int k = 0; k < elems_in_thread; k++) {
			threads.push_back(new std::thread(&addMatrices, a, b, res, dim, i, j));
			if (j == dim) {
				j = 0;
				i++;
			}
			else j++;
		}
		
		threads[l]->join();
	}

	std::cout << "The add Matrix:\n";
	printMatrix(res, dim);

	std::cout << "\n";

	threads.clear();

	i = 0;
	j = 0;

	

	for (int l = 0; l < nr_threads; l++) {
		for (int k = 0; k < elems_in_thread; k++) {
			threads.push_back(new std::thread(&multiplyMatrices, a, b, res, dim, i, j));
			if (j == dim) {
				j = 0;
				i++;
			}
			else j++;
		}
		
		threads[l]->join();
	}

	std::cout << "The multiply Matrix: \n";
	printMatrix(res, dim);
}

int main()
{
	int a[100][100], b[100][100], res[100][100], dim, i, j, nr_threads;
	std::cout << "Enter dimension: ";
	std::cin >> dim;
	std::cout << "\n Enter values for a \n";
	for (i = 0; i < dim; i++) {
		for (j = 0; j < dim; j++) {
			std::cout << "a[" << i << "," << j << "]=";
			std::cin >> a[i][j];
		}
	}
	std::cout << "\n Enter values for b \n";
	for (i = 0; i < dim; i++) {
		for (j = 0; j < dim; j++) {
			std::cout << "b[" << i << "," << j << "]=";
			std::cin >> b[i][j];
		}
	}

	printMatrix(a, dim);
	printMatrix(b, dim);

	nr_threads = 3;

	
	std::vector<std::thread*> threads;

	chrono::steady_clock::time_point start = chrono::steady_clock::now();

	runThreads(threads, a, b, res, dim, nr_threads);
	
	chrono::steady_clock::time_point end = chrono::steady_clock::now();
	cout << "seconds: "
		<< chrono::duration_cast<chrono::milliseconds>(end - start).count();
		

}
