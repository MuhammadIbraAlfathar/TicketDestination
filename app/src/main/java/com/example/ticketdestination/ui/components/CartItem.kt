package com.example.ticketdestination.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ticketdestination.ui.theme.Shapes
import com.example.ticketdestination.ui.theme.TicketDestinationTheme
import com.example.ticketdestination.utils.convertRupiah

@Composable
fun CartItem(
    ticketId: Long,
    photoUrl: String,
    title: String,
    location: String,
    totalPrice: Long,
    count: Int,
    onTicketCountChanged:(id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = photoUrl ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = location,
                fontSize = 10.sp,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
            Text(
                text = convertRupiah(totalPrice),
                fontSize = 10.sp,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        TicketCounter(
            id = ticketId ,
            ticketCount = count,
            onTicketIncreased = {onTicketCountChanged(ticketId, count + 1)} ,
            onTicketDecreased = {onTicketCountChanged(ticketId, count - 1)},
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun CartItemPreview() {
    TicketDestinationTheme {
        CartItem(
            ticketId = 1,
            photoUrl = "",
            title = "Kota Tua" ,
            location = "Jakarta Barat" ,
            totalPrice = 5000,
            count = 0,
            onTicketCountChanged =  {id, count ->}
        )
    }
}