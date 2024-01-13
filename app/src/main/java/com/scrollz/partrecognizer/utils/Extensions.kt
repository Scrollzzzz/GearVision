package com.scrollz.partrecognizer.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.toFineDateTime(): String {
    val inputFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy  HH:mm:ss", Locale.getDefault())
    return try {
        val date = inputFormat.parse(this)
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun String.getImageURL(): String {
    return when (this) {
        "CS120.01.413-1" -> "https://i.postimg.cc/9fJbjbdr/CS120-01-413-1.jpg"
        "CS120.01.413-2" -> "https://i.postimg.cc/8k3y5Zqv/CS120-01-413-2.jpg"
        "CS120.01.413-3" -> "https://i.postimg.cc/zv9pcfn7/CS120-01-413-3.jpg"

        "CS120.07.442-1" -> "https://i.postimg.cc/fbwCFp0G/CS120-07-442-1.jpg"
        "CS120.07.442-2" -> "https://i.postimg.cc/9X46Jw2G/CS120-07-442-2.jpg"
        "CS120.07.442-3" -> "https://i.postimg.cc/50rmXtkJ/CS120-07-442-3.jpg"

        "CS150.01.427-01-1" -> "https://i.postimg.cc/XJq8N5Lx/CS150-01-427-01-1.jpg"
        "CS150.01.427-01-2" -> "https://i.postimg.cc/kXKkdrBG/CS150-01-427-01-2.jpg"
        "CS150.01.427-01-3" -> "https://i.postimg.cc/jC0QhFbk/CS150-01-427-01-3.jpg"

        "SU160.00.404-1" -> "https://i.postimg.cc/rpFbWKgR/SU160-00-404-1.jpg"
        "SU160.00.404-2" -> "https://i.postimg.cc/VNV7C4d2/SU160-00-404-2.jpg"
        "SU160.00.404-3" -> "https://i.postimg.cc/NMKzJS3g/SU160-00-404-3.jpg"

        "SU80.01.426-1" -> "https://i.postimg.cc/JzQvGb3W/SU80-01-426-1.jpg"
        "SU80.01.426-2" -> "https://i.postimg.cc/59ZZG99D/SU80-01-426-2.jpg"
        "SU80.01.426-3" -> "https://i.postimg.cc/C5fskVWq/SU80-01-426-3.jpg"

        "SU80.10.409A-1" -> "https://i.postimg.cc/KYqW5xsq/SU80-10-409-A-1.jpg"
        "SU80.10.409A-2" -> "https://i.postimg.cc/63F1DzKC/SU80-10-409-A-2.jpg"
        "SU80.10.409A-3" -> "https://i.postimg.cc/RZGpMv9S/SU80-10-409-A-3.jpg"

        "ЗВТ86.103К-02-1" -> "https://i.postimg.cc/D0sYFsfH/86-103-02-1.jpg"
        "ЗВТ86.103К-02-2" -> "https://i.postimg.cc/MK7rFyFj/86-103-02-2.jpg"
        "ЗВТ86.103К-02-3" -> "https://i.postimg.cc/mrYq1Jvk/86-103-02-3.jpg"

        "СВМ.37.060-1" -> "https://i.postimg.cc/2jBt4pdG/37-060-1.jpg"
        "СВМ.37.060-2" -> "https://i.postimg.cc/K8msSR6p/37-060-2.jpg"
        "СВМ.37.060-3" -> "https://i.postimg.cc/wBvfMnTh/37-060-3.jpg"

        "СВМ.37.060А-1" -> "https://i.postimg.cc/MTf9bRBH/37-060-1.jpg"
        "СВМ.37.060А-2" -> "https://i.postimg.cc/FztDPRbZ/37-060-2.jpg"
        "СВМ.37.060А-3" -> "https://i.postimg.cc/h4xCXLg3/37-060-3.jpg"

        "СВП-120.00.060-1" -> "https://i.postimg.cc/bN93TqZx/120-00-060-1.jpg"
        "СВП-120.00.060-2" -> "https://i.postimg.cc/q7zw4njw/120-00-060-2.jpg"
        "СВП-120.00.060-3" -> "https://i.postimg.cc/SQWrSm7B/120-00-060-3.jpg"

        "СВП120.42.020-1" -> "https://i.postimg.cc/zvnp4r01/120-42-020-1.jpg"
        "СВП120.42.020-2" -> "https://i.postimg.cc/02sVDzy5/120-42-020-2.jpg"
        "СВП120.42.020-3" -> "https://i.postimg.cc/XYNx1t5T/120-42-020-3.jpg"

        "СВП120.42.030-1" -> "https://i.postimg.cc/HsdtjF6V/120-42-030-1.jpg"
        "СВП120.42.030-2" -> "https://i.postimg.cc/9M9Bq23q/120-42-030-2.jpg"
        "СВП120.42.030-3" -> "https://i.postimg.cc/90jYdkVg/120-42-030-3.jpg"

        "СК20.01.01.01.406-1" -> "https://i.postimg.cc/qvmwPQXg/20-01-01-01-406-1.jpg"
        "СК20.01.01.01.406-2" -> "https://i.postimg.cc/8cBBtbzp/20-01-01-01-406-2.jpg"
        "СК20.01.01.01.406-3" -> "https://i.postimg.cc/k5zc44Hm/20-01-01-01-406-3.jpg"

        "СК20.01.01.02.402-1" -> "https://i.postimg.cc/bvDxKPwF/20-01-01-02-402-1.jpg"
        "СК20.01.01.02.402-2" -> "https://i.postimg.cc/5NgSzprp/20-01-01-02-402-2.jpg"
        "СК20.01.01.02.402-3" -> "https://i.postimg.cc/tRt3029y/20-01-01-02-402-3.jpg"

        "СК30.01.01.02.402-1" -> "https://i.postimg.cc/4xFvLys9/30-01-01-02-402-1.jpg"
        "СК30.01.01.02.402-2" -> "https://i.postimg.cc/W30M17rG/30-01-01-02-402-2.jpg"
        "СК30.01.01.02.402-3" -> "https://i.postimg.cc/7ZLMn084/30-01-01-02-402-3.jpg"

        "СК30.01.01.03.403-1" -> "https://i.postimg.cc/pdwfvS9V/30-01-01-03-403-1.jpg"
        "СК30.01.01.03.403-2" -> "https://i.postimg.cc/4y5VzNWn/30-01-01-03-403-2.jpg"
        "СК30.01.01.03.403-3" -> "https://i.postimg.cc/dt5G337x/30-01-01-03-403-3.jpg"

        "СК50.01.01.404-1" -> "https://i.postimg.cc/sDjp372W/50-01-01-404-1.jpg"
        "СК50.01.01.404-2" -> "https://i.postimg.cc/VkTM9hW9/50-01-01-404-2.jpg"
        "СК50.01.01.404-3" -> "https://i.postimg.cc/5tCzsmsh/50-01-01-404-3.jpg"

        "СК50.02.01.411-1" -> "https://i.postimg.cc/nhMBDxBc/50-02-01-411-1.jpg"
        "СК50.02.01.411-2" -> "https://i.postimg.cc/fLq8YCy6/50-02-01-411-2.jpg"
        "СК50.02.01.411-3" -> "https://i.postimg.cc/hvJpsm7h/50-02-01-411-3.jpg"

        "СПО250.14.190-1" -> "https://i.postimg.cc/6pxYVy65/250-14-190-1.jpg"
        "СПО250.14.190-2" -> "https://i.postimg.cc/DyXxbqTM/250-14-190-2.jpg"
        "СПО250.14.190-3" -> "https://i.postimg.cc/8zdwZNsd/250-14-190-3.jpg"

        else -> this
    }
}
