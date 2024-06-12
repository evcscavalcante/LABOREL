package com.example.laborel.compactação.utils

fun calculateParabolaPoints(
    x1: Float, y1: Float,
    x2: Float, y2: Float,
    x3: Float, y3: Float,
    x4: Float, y4: Float,
    x5: Float, y5: Float,
    minX: Float, maxX: Float
): List<Pair<Float, Float>> {
    fun lagrange(x: Float, xi: FloatArray, yi: FloatArray): Float {
        var result = 0f
        for (i in yi.indices) {
            var term = yi[i]
            for (j in xi.indices) {
                if (i != j) {
                    term *= (x - xi[j]) / (xi[i] - xi[j])
                }
            }
            result += term
        }
        return result
    }

    val xi = floatArrayOf(x1, x2, x3, x4, x5)
    val yi = floatArrayOf(y1, y2, y3, y4, y5)
    val points = mutableListOf<Pair<Float, Float>>()
    var x = minX
    while (x <= maxX) {
        val y = lagrange(x, xi, yi)
        points.add(Pair(x, y))
        x += 0.1f
    }
    return points
}

