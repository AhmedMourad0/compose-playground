package dev.ahmedmourad.compose.millions

data class Duration(val minutes: Int, val seconds: Int) {

    init {
        require(minutes in 0..99)
        require(seconds in 0..99)
    }

    constructor(seconds: Int) : this(
        (seconds / 60).coerceAtMost(99),
        (seconds % 60).coerceAtMost(99)
    )
}

data class Clock(
    val digits: Digit
) {
    constructor(
        topLeft: Digit,
        topRight: Digit,
        bottomLeft: Digit,
        bottomRight: Digit
    ) : this(
        topLeft.concatHorizontal(topRight).concatVertical(bottomLeft.concatHorizontal(bottomRight))
    )
}

fun Digit.concatVertical(other: Digit): Digit {
    return Digit(this.rows + other.rows)
}

fun Digit.concatHorizontal(other: Digit): Digit {
    return Digit(this.rows.zip(other.rows).map {
        DigitRow(it.first.fragments + it.second.fragments)
    })
}

data class Digit(
    val rows: List<DigitRow>
) {
    constructor(vararg rows: DigitRow) : this(rows.toList())
}

data class DigitRow(
    val fragments: List<DigitFragment>
) {
    constructor(vararg fragments: DigitFragment) : this(fragments.toList())
}

data class DigitFragment(
    val firstAngle: Int,
    val secondAngle: Int
)
