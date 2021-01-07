package gin.garin.githubuser

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter (private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.following, R.string.following)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = DetailFragment.newInstance("https://api.github.com/users/${username}/following")
            1 -> fragment = DetailFragment.newInstance("https://api.github.com/users/${username}/followers")
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
    override fun getCount(): Int {
        return 2
    }
}