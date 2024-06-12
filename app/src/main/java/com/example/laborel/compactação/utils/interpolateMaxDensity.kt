package com.example.laborel.compactação.utils

fun interpolateMaxDensity(
    x1: Float, y1: Float,
    x2: Float, y2: Float,
    x3: Float, y3: Float,
    x4: Float, y4: Float,
    x5: Float, y5: Float
): Pair<Float, Float> {
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
    var maxY = Float.MIN_VALUE
    var maxX = 0f
    var x = xi.minOrNull() ?: 0f
    while (x <= (xi.maxOrNull() ?: 0f)) {
        val y = lagrange(x, xi, yi)
        if (y > maxY) {
            maxY = y
            maxX = x
        }
        x += 0.01f
    }
    return Pair(maxX, maxY)
}

