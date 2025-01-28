import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chaesiktak.R
import com.example.chaesiktak.RecommendRecipe

class RecommendRecipeAdapter(private val recipeList: ArrayList<RecommendRecipe>) :
    RecyclerView.Adapter<RecommendRecipeAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.home_recipe_card_imageview)
        val titleView: TextView = itemView.findViewById(R.id.recipe_card_title)
        val subtextView: TextView = itemView.findViewById(R.id.recipe_card_subtext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_recipe_card, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.imageView.setImageResource(recipe.image)
        holder.titleView.text = recipe.title
        holder.subtextView.text = recipe.subtext
    }
}
