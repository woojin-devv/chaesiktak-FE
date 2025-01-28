import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaesiktak.BannerData
import com.example.chaesiktak.R

class BannerAdapter(private val banners: List<BannerData>) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    // ViewHolder 정의
    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.bannerText) // TextView 참조
        val subtitle: TextView = itemView.findViewById(R.id.bannerSubtitle) // TextView 참조
        val image: ImageView = itemView.findViewById(R.id.bannerImage) // ImageView 참조
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // banner1.xml을 인플레이트
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.banner1, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val banner = banners[position]
        holder.title.text = banner.title
        holder.subtitle.text = banner.subtitle
        holder.image.setImageResource(banner.imageResId) // 이미지 리소스 설정
    }

    override fun getItemCount(): Int = banners.size
}
