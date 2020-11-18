package adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cvmaaker.AchievementsFragment;
import com.example.cvmaaker.EducationFragment;
import com.example.cvmaaker.ExperienceFragment;
import com.example.cvmaaker.PersonalInformationFragment;


public class ViewFragmentAdapter extends FragmentPagerAdapter {

    public ViewFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PersonalInformationFragment personalInformationFragment = new PersonalInformationFragment();
                return personalInformationFragment;
            case 1:
                AchievementsFragment achievementsFragment = new AchievementsFragment();
                return achievementsFragment;

            case 2:

                EducationFragment educationFragment = new EducationFragment();
                return educationFragment;
            case 3:
                ExperienceFragment experienceFragment = new ExperienceFragment();
                return experienceFragment;
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "personal";
            case 1:
                return "#$";
            case 2:
                return "***";

            case 3:
                return "**";
        }

        return super.getPageTitle(position);
    }
}