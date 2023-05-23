package com.example.ticketdestination.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ticketdestination.ui.theme.TicketDestinationTheme
import com.example.ticketdestination.utils.convertRupiah

@Composable
fun TicketItem(
    photoUrl: String,
    title: String,
    price: Long,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = photoUrl ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp)
            )
            Text(
                text = title,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = convertRupiah(price = price),
                fontSize = 10.sp,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.paddingFromBaseline(bottom = 24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketItemPreview(){
    TicketDestinationTheme {
        TicketItem(photoUrl = "", title = "Taman Mini" , price = 20000 )
    }
}