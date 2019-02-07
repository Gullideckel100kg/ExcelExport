package h.gullideckel.jobsexcelexport.DynamicViewPager;

import android.support.v4.view.ViewPager;
import android.view.View;

public class PageHandler
{
    private ViewPager viewPager;

    public PageHandler(ViewPager viewPager)
    {
        this.viewPager = viewPager;
    }

    public void addView (View newPage, ViewPagerDynamicAdapter adapter)
    {
        int pageIndex = adapter.addView (newPage);
    }

    public void removeView (View defunctPage, ViewPagerDynamicAdapter adapter)
    {
        int pageIndex = adapter.removeView (viewPager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == adapter.getCount())
            pageIndex--;
        if(pageIndex >  -1)
            viewPager.setCurrentItem (pageIndex);
    }

    // Get the currently displayed page.
    public View getCurrentPage (ViewPagerDynamicAdapter adapter)
    {
        return adapter.getView (viewPager.getCurrentItem());
    }

    public void changeCurrentView(ViewPagerDynamicAdapter adapter, View v)
    {
        int position = viewPager.getCurrentItem();

        adapter.removeView(viewPager, position);
        adapter.addView(v, position);
        adapter.notifyDataSetChanged();

        viewPager.setCurrentItem(position);
    }

    //"pageToShow" must currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow, ViewPagerDynamicAdapter adapter)
    {
        viewPager.setCurrentItem (adapter.getItemPosition (pageToShow), true);
    }
}
