package dev.ahmedmourad.compose.millions

private object Fragments {
    val TOP_LEFT_CORNER = DigitFragment(0, 270)
    val TOP_RIGHT_CORNER = DigitFragment(180, 270)
    val BOTTOM_LEFT_CORNER = DigitFragment(90, 0)
    val BOTTOM_RIGHT_CORNER = DigitFragment(90, 180)
    val HORIZONTAL = DigitFragment(0, 180)
    val VERTICAL = DigitFragment(90, 270)
    val DEAD = DigitFragment(225, 225)
}

object Digits {

    fun from(digit: Int?): Digit {
        return when (digit) {
            null -> DEAD
            0 -> ZERO
            1 -> ONE
            2 -> TWO
            3 -> THREE
            4 -> FOUR
            5 -> FIVE
            6 -> SIX
            7 -> SEVEN
            8 -> EIGHT
            9 -> NINE
            else -> throw IllegalArgumentException("Digit must be between 0 and 9: $digit")
        }
    }

    private val DEAD = Digit(
        DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
        )
    )

    private val ZERO = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )

    private val ONE = Digit(
        DigitRow(
            Fragments.DEAD,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.DEAD,
        ), DigitRow(
            Fragments.DEAD,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.DEAD,
        )
    )

    private val TWO = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )

    private val THREE = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )

    private val FOUR = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD,
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.DEAD
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.DEAD
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.TOP_RIGHT_CORNER
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.TOP_LEFT_CORNER,
            Fragments.BOTTOM_RIGHT_CORNER
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.DEAD,
        )
    )

    private val FIVE = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )

    private val SIX = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )

    private val SEVEN = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            DigitFragment(180, 225),
            DigitFragment(90, 225)
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            DigitFragment(45, 270),
            DigitFragment(45, 270),
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.VERTICAL,
            Fragments.VERTICAL,
            Fragments.DEAD
        ), DigitRow(
            Fragments.DEAD,
            Fragments.DEAD,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.DEAD,
        )
    )

    private val EIGHT = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )

    private val NINE = Digit(
        DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.VERTICAL,
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.TOP_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.TOP_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
            Fragments.VERTICAL
        ), DigitRow(
            Fragments.BOTTOM_LEFT_CORNER,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.HORIZONTAL,
            Fragments.BOTTOM_RIGHT_CORNER,
        )
    )
}
