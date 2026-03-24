package com.example.khulakasi

import android.graphics.Paint as AndroidPaint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.border
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChartScreen() {
    val darkColor = Color(0xFF052224)
    val lightColor = Color(0xFFF1FFF3)

    var selectedTab by remember { mutableStateOf("Income") }      // Income / Expenses
    var selectedPeriod by remember { mutableStateOf("Weekly") }   // Weekly / Monthly / Annually

    val topDarkHeight = 360.dp
    val lightOffset = 300.dp

    // Whole screen dark background (keeps dark band at top)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkColor)
    ) {
        // Dark top band (visual only)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topDarkHeight)
                .background(darkColor)
        )

        // Header (top)
        Text(
            text = "Analytics Overview",
            color = Color(0xFFF1FFF3),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 28.dp)
        )

        // NEW: Records Summary Container on the dark green part
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(180.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color(0x40000000),
                    spotColor = Color(0x40000000)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Header row with title and percentage indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Records Summary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF093030)
                    )

                    // Percentage indicator container
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF1FFF3))
                            .border(
                                width = 1.dp,
                                color = Color(0xFF093030),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "+10.5% since last month",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF093030)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Big number
                Text(
                    text = "10,643",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF093030)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Progress bars (small vertical rounded edge cubes)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    // First 5 bars in #0068FF
                    repeat(5) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF0068FF))
                        )
                    }
                    // Next 3 bars in #6DB6FE
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF6DB6FE))
                        )
                    }
                    // Last 4 bars in #3299FF
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFF3299FF))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Legend with colored dots and labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LegendDotItem(Color(0xFF0068FF), "Passengers")
                    LegendDotItem(Color(0xFF6DB6FE), "Trips")
                    LegendDotItem(Color(0xFF3299FF), "Expenses")
                }
            }
        }

        // Scrollable column: the light/mint block is placed inside this Column so it will scroll with the content.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Spacer to allow the top dark area to be visible above the rounded light block
            Spacer(modifier = Modifier.height(lightOffset))

            // White / mint rounded container that *scrolls* with the column content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 64.dp, topEnd = 64.dp))
                    .background(lightColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 24.dp, bottom = 100.dp)
                ) {
                    // Top card: contains the Income/Expenses toggle
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(8.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            SlidingTabContainer(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Doughnut Chart Card
                    DoughnutChartCard(
                        selectedTab = selectedTab,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Period selector
                    PeriodSelectorContainer(
                        selectedPeriod = selectedPeriod,
                        onPeriodSelected = { selectedPeriod = it }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Bar Chart Container
                    BarChartContainer(
                        barData = getBarDataForPeriod(selectedPeriod)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Overview Chart Container
                    OverviewChartContainer(
                        dataPoints = getOverviewDataForPeriod(selectedPeriod),
                        xLabels = getMonthLabels(),
                        maxY = 500_000f
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

/* -------------------------
   Legend Dot Item for Records Summary
   ------------------------- */
@Composable
fun LegendDotItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF093030)
        )
    }
}

/* -------------------------
   Sliding tab (Income / Expenses)
   (kept your styling/behavior)
   ------------------------- */
@Composable
fun SlidingTabContainer(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    val animatedX = if (selectedTab == "Income") 8.dp else 138.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFFEDF6F2))
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(42.dp)
                .offset(x = animatedX, y = 4.dp)
                .clip(RoundedCornerShape(21.dp))
                .background(Color(0xFFDFF7E2))
        )

        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onTabSelected("Income") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Income",
                    fontSize = 16.sp,
                    fontWeight = if (selectedTab == "Income") FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedTab == "Income") Color(0xFF093030) else Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onTabSelected("Expenses") },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Expenses",
                    fontSize = 16.sp,
                    fontWeight = if (selectedTab == "Expenses") FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedTab == "Expenses") Color(0xFF093030) else Color.Black
                )
            }
        }
    }
}

/* -------------------------
   Doughnut Card (UPDATED with proper Expenses chart like Income)
   ------------------------- */
@Composable
fun DoughnutChartCard(selectedTab: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(animationSpec = tween(300))
        ) {
            when (selectedTab) {
                "Income" -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            LegendItem(Color(0xFF007BFF), "Progress", "75%")
                            LegendItem(Color(0xFFDC3545), "Loss", "25%")
                        }

                        Canvas(modifier = Modifier.size(160.dp)) {
                            val center = Offset(size.width / 2, size.height / 2)
                            val radius = size.minDimension / 3f
                            val stroke = 22.dp.toPx()

                            drawArc(
                                color = Color(0xFF007BFF),
                                startAngle = -90f,
                                sweepAngle = 360f * 0.75f,
                                useCenter = false,
                                style = Stroke(width = stroke),
                                size = Size(radius * 2, radius * 2),
                                topLeft = Offset(center.x - radius, center.y - radius)
                            )
                            drawArc(
                                color = Color(0xFFDC3545),
                                startAngle = -90f + (360f * 0.75f),
                                sweepAngle = 360f * 0.25f,
                                useCenter = false,
                                style = Stroke(width = stroke),
                                size = Size(radius * 2, radius * 2),
                                topLeft = Offset(center.x - radius, center.y - radius)
                            )
                        }
                    }
                }
                "Expenses" -> {
                    // UPDATED: Expenses chart now follows same pattern as Income chart
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            LegendItem(Color(0xFFFF8C00), "Fuel", "45%")
                            LegendItem(Color(0xFF007BFF), "Service", "30%")
                            LegendItem(Color(0xFF28A745), "Line Fee", "15%")
                            LegendItem(Color(0xFFDC3545), "Other", "10%")
                        }

                        Canvas(modifier = Modifier.size(160.dp)) {
                            val center = Offset(size.width / 2, size.height / 2)
                            val radius = size.minDimension / 3f
                            val stroke = 22.dp.toPx()

                            // Draw arcs for each expense category
                            var startAngle = -90f

                            // Fuel - 45%
                            drawArc(
                                color = Color(0xFFFF8C00),
                                startAngle = startAngle,
                                sweepAngle = 360f * 0.45f,
                                useCenter = false,
                                style = Stroke(width = stroke),
                                size = Size(radius * 2, radius * 2),
                                topLeft = Offset(center.x - radius, center.y - radius)
                            )
                            startAngle += 360f * 0.45f

                            // Service - 30%
                            drawArc(
                                color = Color(0xFF007BFF),
                                startAngle = startAngle,
                                sweepAngle = 360f * 0.30f,
                                useCenter = false,
                                style = Stroke(width = stroke),
                                size = Size(radius * 2, radius * 2),
                                topLeft = Offset(center.x - radius, center.y - radius)
                            )
                            startAngle += 360f * 0.30f

                            // Line Fee - 15%
                            drawArc(
                                color = Color(0xFF28A745),
                                startAngle = startAngle,
                                sweepAngle = 360f * 0.15f,
                                useCenter = false,
                                style = Stroke(width = stroke),
                                size = Size(radius * 2, radius * 2),
                                topLeft = Offset(center.x - radius, center.y - radius)
                            )
                            startAngle += 360f * 0.15f

                            // Other - 10%
                            drawArc(
                                color = Color(0xFFDC3545),
                                startAngle = startAngle,
                                sweepAngle = 360f * 0.10f,
                                useCenter = false,
                                style = Stroke(width = stroke),
                                size = Size(radius * 2, radius * 2),
                                topLeft = Offset(center.x - radius, center.y - radius)
                            )
                        }
                    }
                }
            }
        }
    }
}

/* -------------------------
   Period Selector (unchanged)
   ------------------------- */
@Composable
fun PeriodSelectorContainer(selectedPeriod: String, onPeriodSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Weekly", "Monthly", "Annually").forEach { filter ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(45.dp)
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selectedPeriod == filter) Color(0xFF093030) else Color.White)
                    .clickable { onPeriodSelected(filter) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = filter,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (selectedPeriod == filter) Color.White else Color(0xFF093030)
                )
            }
        }
    }
}

/* -------------------------
   Bar Chart (Y-axis shows 0%,25%,50%,75%,100%)
   ------------------------- */
@Composable
fun BarChartContainer(barData: List<Float>) {
    val colors = listOf(
        Color(0xFF007BFF),
        Color(0xFF28A745),
        Color(0xFFFF8C00),
        Color(0xFFDC3545),
        Color(0xFF6F42C1)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Progress Overview",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)) {
                val axisOffset = 40.dp.toPx()
                val chartHeight = size.height - 40.dp.toPx()
                val barCount = barData.size
                val barWidth = (size.width - axisOffset - 30.dp.toPx()) / (barCount * 1.8f)
                val maxValue = (barData.maxOrNull() ?: 1f)

                // Y axis
                drawLine(
                    color = Color.Gray,
                    start = Offset(axisOffset, 8.dp.toPx()),
                    end = Offset(axisOffset, chartHeight),
                    strokeWidth = 2.dp.toPx()
                )
                // X axis
                drawLine(
                    color = Color.Gray,
                    start = Offset(axisOffset, chartHeight),
                    end = Offset(size.width - 10.dp.toPx(), chartHeight),
                    strokeWidth = 2.dp.toPx()
                )

                // Y labels in percent (left aligned)
                val percentSteps = listOf(0f, 0.25f, 0.5f, 0.75f, 1f)
                val paintLeft = AndroidPaint().apply {
                    isAntiAlias = true
                    textSize = 22f
                    color = android.graphics.Color.DKGRAY
                    textAlign = android.graphics.Paint.Align.LEFT
                }

                percentSteps.forEach { pct ->
                    val yPos = chartHeight - pct * (chartHeight - 30.dp.toPx())
                    // faint horizontal grid
                    drawLine(
                        color = Color(0x22000000),
                        start = Offset(axisOffset, yPos),
                        end = Offset(size.width - 10.dp.toPx(), yPos),
                        strokeWidth = 1.dp.toPx()
                    )
                    val label = "${(pct * 100).toInt()}%"
                    drawContext.canvas.nativeCanvas.drawText(
                        label,
                        6.dp.toPx(),
                        yPos + 8.dp.toPx(),
                        paintLeft
                    )
                }

                // Bars
                barData.forEachIndexed { i, v ->
                    val scaled = (v / maxValue) * (chartHeight - 30.dp.toPx())
                    val left = axisOffset + 16.dp.toPx() + (i * barWidth * 1.8f)
                    drawRect(
                        color = colors[i % colors.size],
                        topLeft = Offset(left, chartHeight - scaled),
                        size = Size(barWidth, scaled)
                    )
                }
            }
        }
    }
}

/* -------------------------
   Overview Chart (Line) - bigger
   ------------------------- */
@Composable
fun OverviewChartContainer(dataPoints: List<Float>, xLabels: List<String>, maxY: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Overview Chart",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            LineChart(
                dataPoints = dataPoints,
                xLabels = xLabels,
                maxY = maxY,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            )
        }
    }
}

/* -------------------------
   Line Chart: Y labels = 0, 125k, 250k, 375k, 500k (no "R"); labels smaller and left aligned
   ------------------------- */
@Composable
fun LineChart(
    dataPoints: List<Float>,
    xLabels: List<String>,
    maxY: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val strokeWidth = 3.dp.toPx()
        val dotRadius = 4.dp.toPx()
        val axisOffset = 56.dp.toPx()
        val chartHeight = size.height - 60.dp.toPx()
        val chartWidth = size.width - axisOffset - 16.dp.toPx()

        // Draw axes
        drawLine(
            color = Color.Gray,
            start = Offset(axisOffset, 8.dp.toPx()),
            end = Offset(axisOffset, chartHeight),
            strokeWidth = 2.dp.toPx()
        )
        drawLine(
            color = Color.Gray,
            start = Offset(axisOffset, chartHeight),
            end = Offset(size.width - 10.dp.toPx(), chartHeight),
            strokeWidth = 2.dp.toPx()
        )

        // Y labels smaller + no "R"
        val yTicks = 4
        val perTick = maxY / yTicks
        val paintLeft = AndroidPaint().apply {
            isAntiAlias = true
            textSize = 16f // reduced text size
            color = android.graphics.Color.DKGRAY
            textAlign = android.graphics.Paint.Align.LEFT
        }

        for (i in 0..yTicks) {
            val yValue = perTick * i
            val yPos = chartHeight - (yValue / maxY) * (chartHeight - 20.dp.toPx())

            // faint grid line
            drawLine(
                color = Color(0x22000000),
                start = Offset(axisOffset, yPos),
                end = Offset(size.width - 10.dp.toPx(), yPos),
                strokeWidth = 1.dp.toPx()
            )

            val label = if (yValue == 0f) "0" else "${(yValue / 1000).toInt()}k"
            drawContext.canvas.nativeCanvas.drawText(
                label,
                6.dp.toPx(),
                yPos + 6.dp.toPx(),
                paintLeft
            )
        }

        // Plot points
        if (dataPoints.isNotEmpty()) {
            val points = mutableListOf<Offset>()
            val stepX = if (dataPoints.size > 1) chartWidth / (dataPoints.size - 1) else chartWidth

            dataPoints.forEachIndexed { idx, v ->
                val x = axisOffset + (idx * stepX)
                val y = chartHeight - (v / maxY) * (chartHeight - 40.dp.toPx())
                points.add(Offset(x, y))
            }

            val path = Path().apply {
                if (points.isNotEmpty()) {
                    moveTo(points.first().x, points.first().y)
                    for (i in 1 until points.size) lineTo(points[i].x, points[i].y)
                }
            }

            drawPath(path = path, color = Color(0xFF007BFF), style = Stroke(width = strokeWidth))
            val fillPath = Path().apply {
                addPath(path)
                lineTo(points.last().x, chartHeight)
                lineTo(points.first().x, chartHeight)
                close()
            }
            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF007BFF).copy(alpha = 0.25f),
                        Color(0xFF007BFF).copy(alpha = 0.05f)
                    )
                )
            )

            points.forEach { pt ->
                drawCircle(Color(0xFF007BFF), radius = dotRadius, center = pt)
                drawCircle(Color.White, radius = dotRadius * 0.6f, center = pt)
            }

            // X labels (centered under point)
            val paintX = AndroidPaint().apply {
                isAntiAlias = true
                textSize = 14f
                color = android.graphics.Color.DKGRAY
                textAlign = android.graphics.Paint.Align.CENTER
            }
            points.forEachIndexed { i, pt ->
                val label = if (i < xLabels.size) xLabels[i] else (i + 1).toString()
                drawContext.canvas.nativeCanvas.drawText(
                    label,
                    pt.x,
                    chartHeight + 22.dp.toPx(),
                    paintX
                )
            }
        }
    }
}

/*-------------------------
   Data helpers (unchanged)
   ------------------------- */
fun getBarDataForPeriod(period: String): List<Float> = when (period) {
    "Weekly" -> listOf(250f, 400f, 300f, 200f, 100f)
    "Monthly" -> listOf(800f, 950f, 700f, 600f, 400f)
    "Annually" -> listOf(5000f, 6500f, 7000f, 4800f, 3200f)
    else -> emptyList()
}

fun getOverviewDataForPeriod(period: String): List<Float> = when (period) {
    "Weekly" -> listOf(200f, 400f, 600f, 300f, 500f, 350f, 450f)
    "Monthly" -> listOf(1200f, 2200f, 3100f, 2800f, 3500f, 3000f, 4000f, 3800f, 4200f, 4500f, 4700f, 4900f)
    "Annually" -> listOf(8000f, 10000f, 12000f, 15000f, 17000f, 19000f, 21000f, 22000f, 25000f, 27000f, 30000f, 32000f)
    else -> emptyList()
}

fun getMonthLabels(): List<String> =
    listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

/* -------------------------
   Legend Item (unchanged)
   ------------------------- */
@Composable
fun LegendItem(color: Color, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("$label: $value", fontSize = 14.sp, color = Color.Black)
    }
}