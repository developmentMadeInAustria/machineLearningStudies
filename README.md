# machineLearningStudies

This repository consists of my work on Machine Learning, especially on topics, which are covered by the [Udacity](https://www.udacity.com/) online course about Supervised, Unsupervised and  Reinforcement Learning

## 1. Optimization

The first part of this repository covers some optimization algorithms, such as Hill Climbing, Simulated Annealing, Genetic Algorithms and also a binary form of MIMIC is covered. All code to these algorithms will be in the optimization folders.

### a, Hill Climbing

Hill Climbing is an algorithm, which always goes upwards, until it reaches a maximum. If you have more local maxima, the probabilty is not so high to get the global maximum by the first try, so the code in this repository includes a restart method, with n-restarts, which returns the highest value. To learn more about Hill Climbing, click [here](https://en.wikipedia.org/wiki/Hill_climbing).

### b, Simulated Annealing

In contrast to Hill Climbing, you are at Simulated Annealing allowed to go downward or to *explore*. Here you have a fixed temperature at the beginning and it goes down step by step, while the algorithm works. The higher the temperature, the higher is the probability to go downward a bigger step and as the temperature cools down, you take only small steps downward and more steps upward until you reach a maximum, where you aren't allowed to go down any more. For more information, click [here](https://en.wikipedia.org/wiki/Simulated_annealing).

### c, Genetic Algorithms

Genetic Algorithms are a completely different way to analyze data and to find an optimum. Here you allways take the upper half of your samples, and *mix* them, the right terminus is crossover. That way you always get new samples and each round you take the upper half and mix them again until the data is good enough. If you want to learn more, click [here](https://en.wikipedia.org/wiki/Genetic_algorithm).

### d, Mimic 

At the Mimic algorithm the goal is to gain structure instead of just getting one end point for a distribution. Structure means in this context to get a probability distribution for every step in time. To reach this goal we first create samples from a distribution. Then we set a threshold t, and every value of the samples we created above has to be above that threshold, otherwise we remove it. Now we want to create a structure, which we can easily sample from and in which every sample, where the value is above t, has the same likelihood. One of the easiest ways to do this, is to find a dependency tree. In such a configuration every single element has one parent, except the root, and the parent is the element, which maximizes the probability that the next generation element is true, if the parent element is true. So we get a dependency tree, which fits to the goals we defined above. To reach a tree like this, we have to find calculate the [Mutual Information](https://en.wikipedia.org/wiki/Mutual_information) between all points. After that we just have to find a [minimum spanning tree](https://en.wikipedia.org/wiki/Minimum_spanning_tree) of the negative forms of all the negative mutual information, because we want to maximize mutual information and it is easier to calculate a minimum spanning tree instead of a maximum one so we switch signs. We create our spanning tree with [prim](https://en.wikipedia.org/wiki/Prim%27s_algorithm) and now we have our tree. Now we just have to sample from our spanning tree and iterate until our result is good enough. A binary form of the MIMIC algorithm is implemented in this repository.

> Later there will follow more algorithms about Machine Learning
