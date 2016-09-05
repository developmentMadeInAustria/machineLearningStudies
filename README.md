# machineLearningStudies

This repository consists of my work on Machine Learning, especially on topics, which are covered by the [Udacity](https://www.udacity.com/) online course about Supervised, Unsupervised and  Reinforcement Learning

### 1. Optimization

The first part of this repository covers some optimization algorithms, such as Hill Climbing, Simulated Annealing, Genetic Algorithms and later on also the MIMIC algorithm will be covered. All code to these algorithms will be in the optimization folders.

##### a, Hill Climbing

Hill Climbing is an algorithm, which always goes upwards, until it reaches a maximum. If you have more local maxima, the probabilty is not so high to get the global maximum by the first try, so the code in this repository includes a restart method, with n-restarts, which returns the highest value. To learn more about Hill Climbing, click [here](https://en.wikipedia.org/wiki/Hill_climbing).

##### b, Simulated Annealing

In contrast to Hill Climbing, you are at Simulated Annealing allowed to go downward or to *explore*. Here you have a fixed temperature at the beginning and it goes down step by step, while the algorithm works. The higher the temperature, the higher is the probability to go downward a bigger step and as the temperature cools down, you take only small steps downward and more steps upward until you reach a maximum, where you aren't allowed to go down any more. For more information, click [here](https://en.wikipedia.org/wiki/Simulated_annealing).

##### c, Genetic Algorithms

Genetic Algorithms are a completely different way to analyze data and to find an optimum. Here you allways take the upper half of your samples, and *mix* them, the right terminus is crossover. That way you always get new samples and each round you take the upper half and mix them again until the data is good enough. If you want to learn more, click [here](https://en.wikipedia.org/wiki/Genetic_algorithm).

> Later there will follow more algorithms about Machine Learning
