package com.ssafy.jaljara.ui.screen.child

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ssafy.jaljara.R
import com.ssafy.jaljara.data.Content
import com.ssafy.jaljara.data.DummyChildSleepInfo
import com.ssafy.jaljara.data.DummyDataProvider
import com.ssafy.jaljara.ui.screen.ChildSetTimeCard
import com.ssafy.jaljara.ui.screen.Children
import com.ssafy.jaljara.ui.screen.CurrentRewardContainer

var dummyChildSleepInfo = DummyChildSleepInfo()

@Composable
fun ChildMainView(){
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MissionTodayContainer(dummyChildSleepInfo.currentReward)
            SetSllepTimeContainer(dummyChildSleepInfo.targetBedTime,dummyChildSleepInfo.targetWakeupTime)
            RewardStatusContainer(dummyChildSleepInfo.streakCount)
            ContentContainer(contents = DummyDataProvider.contentList)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MissionTodayContainer(todayMission: String){
    Column() {
        Text(text = "오늘의 미션", fontWeight = FontWeight.Bold, color = Color.White)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            content = {
                Box(){
                    Image(
                        painter = painterResource(R.drawable.mission_bg),
                        contentDescription = "background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    reloadMissionButton(
                        modifier = Modifier
//                            .align(Alignment.End)
                            .clickable {
                                Log.d("missionReload", "미션 재설정 호출")
                                //미션 재설정API 호출
                            },
                    )
                    Text(text = "$todayMission",
                        color = Color.White,
                        modifier = Modifier
                            .padding(bottom = 20.dp)
//                            .align(Alignment.CenterHorizontally)
                    )
                }


            }
        )
    }
}

@Composable
fun reloadMissionButton(modifier: Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(text = "재설정")
        Image(
            painter = painterResource(id = R.drawable.ic_reload),
            contentDescription = null
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetSllepTimeContainer(targetBedTime: String, targetWakeupTime:String) {
    Column() {
        Text(text = "설정된 수면시간", fontWeight = FontWeight.Bold, color = Color.White)
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            content = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SetTimeContainer(painterResource(id = R.drawable.ic_launcher_foreground), "취침시간", "${targetBedTime.substring(0,5)}")
                    SetTimeContainer(painterResource(id = R.drawable.ic_launcher_foreground), "기상시간", "${targetWakeupTime.substring(0,5)}")
                }
            }
        )
    }
}

@Composable
fun SetTimeContainer(img : Painter, title : String, setTime : String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
    ) {
        Image(painter = img,
            contentDescription = null,
        )
        Text(text = title)
        Text(text = setTime)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardStatusContainer(streakCount:Int) {
    var remainCnt by remember { mutableStateOf(7-streakCount) }

    Column() {
        Text(text = "보상 획득 현황", fontWeight = FontWeight.Bold, color = Color.White)
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
//                        modifier = Modifier.size(60.dp, 60.dp)
                    )
                    Text(text = "연속 $remainCnt 번만 성공하면 보상을 획득할 수 있어요!",
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        )
    }
}


@Composable
fun ContentContainer(contents: List<Content>) {
    Column() {
        Text(text = "컨텐츠 바로가기", fontWeight = FontWeight.Bold, color = Color.White)
        Column() {
            LazyRow() {
                items(contents){ ContentCard(it) }
            }
            LazyRow() {
                items(contents){ ContentCard(it) }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentCard(content: Content) {
    // 이미지 비트맵
    val bitmap : MutableState<Bitmap?> = mutableStateOf(null)

    //현재 컨텍스트 가져오기
    //이걸 비트맵으로 받겠다
    //어떤 URL인데
    //
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(content.url)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                //이미지 비트맵이 다 로드가 됐을때 들어오는 메소드
                bitmap.value = resource //글라이더 라이브러리를 통해 다운받은 비트맵
            }
            override fun onLoadCleared(placeholder: Drawable?) { }
        })

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .padding(end = 3.dp, bottom = 5.dp)
    ) {
        // 비트 맵이 있다면
        bitmap.value?.asImageBitmap()?.let{fetchedBitmap ->
            Image(bitmap = fetchedBitmap,
                contentScale = ContentScale.Fit,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp,60.dp)
            )
        } ?: Image(painter = painterResource(R.drawable.ic_no_content),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        ) // 비트맵이 없다면
    }
}

@Preview(showBackground = true)
@Composable
fun ChildMainScreenView() {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(20.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MissionTodayContainer(dummyChildSleepInfo.currentReward)
            SetSllepTimeContainer(
                dummyChildSleepInfo.targetBedTime,
                dummyChildSleepInfo.targetWakeupTime
            )
            RewardStatusContainer(dummyChildSleepInfo.streakCount)
            ContentContainer(contents = DummyDataProvider.contentList)
        }
    }
}