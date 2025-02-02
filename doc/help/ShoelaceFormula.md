# Shoelace formula

The ``shoelace formula`` (Gauss's area formula or ) is a mathematical algorithm to determine the area of a simple polygon whose vertices are described by their Cartesian coordinates in the plane.

Link: [Wiki: Shoelace_formula](https://en.wikipedia.org/wiki/Shoelace_formula)

## Global

A = abs(( sum ( point\[i\]\[x\] * (point\[i+1\]\[y\] - point\[i-1\]\[y\]) ) ) / 2)

```python
abs(
    sum(
        points[i][0] * (points[i - 1][1] - points[(i + 1) % len(points)][1]) for i in range(len(points))
    )
) // 2
```

## Tiles-World

In tiles world ``shoelace formula`` give us not the full area. We are missing ~``the half`` of boarder. To fix it we can use ``Pick's theorem`` ([Wiki](https://en.wikipedia.org/wiki/Pick's_theorem)):

```
A = i + b/2 - 1
```

- i: number of points interior to the polygon
- b: number of integer points on its boundary

=> i = A - b/2 + 1

We compute A with ``shoelace formula`` and then we compute FullArea with:

```python
A = abs(sum(points[i][0] * (points[i - 1][1] - points[(i + 1) % len(points)][1]) for i in range(len(points)))) // 2

i = A - b // 2 + 1

FullArea = i + b
```

### Example

```text
#######
#######
#######
..#####
..#####
#######
#####..
#######
.######
.######
```

or

b is count of boundary (count of #) in:

```text
#######
#.....#
###...#
..#...#
..#...#
###.###
#...#..
##..###
.#....#
.######
```

b = 38

A = shoelace formula = ... = 42

i = 42 - 38/2 + 1 = 24

FullArea = i + b = 24 + 38 = 62
