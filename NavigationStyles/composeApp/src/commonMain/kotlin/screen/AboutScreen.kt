package screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutScreen() {

    var content: List<List<String>> = listOf(
        listOf(
            "Deepak Kaligotla",
            "Mobile Application Developer, have 5.5 years of experience in IT Support",
            "I am Highly motivated and detail-oriented individual with a strong passion for mobile app development, particularly on the Android platform.",
            "Seeking an entry-level position as an Android developer to leverage my programming skills and knowledge of Java and Kotlin to contribute to innovative app projects.",
            "Eager to learn and grow within a dynamic team, while delivering high-quality code and user experiences",
            "I am currently looking for a job opportunity"
        ),
        listOf(
            "Motive",
            "I made this application to make Data Structures & Algorithms understandable inside Android app on a huge table (155,599 rows)",
            "Topics I covered in this application are",
            "Data Structure:\nArray/Vector, LinkedLists, Queue, Stack, HashTable",
            "Search:\nLinear, Binary, Breadth First, Depth First, Exponential, Interpolation, Jump, Sequential",
            "Sort:\nSelection, Insertion, Merge, Heap, Bubble, Quick, Shell"
        )
    )

    var testData: String = "Test Data"

    Scaffold(
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val pagerState = rememberPagerState(initialPage = 0, pageCount = { content.size })
            HorizontalPager(
                state = pagerState,
            ) { page ->
                LazyColumn {
                    val pageText = content[page]
                    item {
                        for (index in pageText.indices) {
                            Text(
                                text = buildAnnotatedString {
                                    append(pageText[index])
                                    if (index == 0) {
                                        addStyle(
                                            style = SpanStyle(
                                                color = Color.Red,
                                                fontSize = 32.sp,
                                                fontFamily = FontFamily.Cursive
                                            ),
                                            start = 0,
                                            end = 1
                                        )
                                    }
                                    if (pageText[index] == testData) {
                                        addStyle(
                                            style = SpanStyle(
                                                fontWeight = FontWeight.Bold,
                                                textDecoration = TextDecoration.LineThrough
                                            ),
                                            start = 0,
                                            end = testData.length
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                fontSize = 18.sp,
                                style = TextStyle(
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }
                        Text(
                            text = "Page ${page + 1}",
                            textAlign = TextAlign.Right,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                }
            }
            Text(
                text = "Slide left for next Page",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }
    }
}