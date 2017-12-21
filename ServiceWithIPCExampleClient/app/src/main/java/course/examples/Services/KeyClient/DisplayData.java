package course.examples.Services.KeyClient;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by ram_n on 12/5/2017.
 */

public class DisplayData extends Activity implements TitleFragment.ListSelectionListener {

    public static final String INTENT_STRING="INTENT_STRING";
    public static final String INTENT_DATA="INTENT_DATA";
    public static final String MONTHLY_CASH="MONTHLY_CASH";
    public static final String DAILY_CASH="DAILY_CASH";
    public static final String YEARLY_AVERAGE="YEARLY_AVERAGE";

    public static String[] mTitleArray;
    //String that holds the data of the 3 methods
    public static String[] data;

    public final DataFragment mDataFragment = new DataFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout mTitleLayout, mDataLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG ="MainActivity";



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_data);


        // Get the string arrays with the titles and qutoes
        mTitleArray = getResources().getStringArray(R.array.Titles);

        data =new String[3];

        //Getting the references of the frame layouts
        mTitleLayout=(FrameLayout) findViewById(R.id.title_fragment_container);
        mDataLayout=(FrameLayout) findViewById(R.id.data_fragment_container);

        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the TitleFragment to the layout
        // Changed add() to replace() to avoid overlapping fragments
        if(savedInstanceState==null)
            fragmentTransaction.replace(R.id.title_fragment_container,
                    new TitleFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });

        Intent intent=getIntent();
        check(intent);
    }

    //Checks and inserts the data into the respective positions to send to display in DataFragment.java
    private void check(Intent i)
    {
        switch(i.getStringExtra(INTENT_STRING))
        {
            case MONTHLY_CASH:  data[0]=i.getStringExtra(INTENT_DATA);
                                break;

            case DAILY_CASH:    data[1]=i.getStringExtra(INTENT_DATA);
                                break;

            case YEARLY_AVERAGE:data[2]=i.getStringExtra(INTENT_DATA);
                                break;
        }
    }

    private void setLayout() {

        // Determine whether the LandmarkWebviewFragment has been added
        if (!mDataFragment.isAdded()) {
            // Make the TitleFragment occupy the entire layout
            mTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mDataLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {

            // Getting the orientation of the device
            Display display = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int rotation = display.getRotation();

            // Checking if the device is vertical
            if(rotation== Surface.ROTATION_0 || rotation== Surface.ROTATION_180)
            {
                // Make the LandmarkNameFragment layout take 1/3 of the layout's width
                mTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));

                // Make the LandmarkWebviewFragment take 2/3's of the layout's width
                mDataLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,
                        MATCH_PARENT));
            }

            // Checking if the device is horizontal
            else if(rotation== Surface.ROTATION_90 || rotation== Surface.ROTATION_270)
            {
                // Make the LandmarkNameFragment take 1/3 of the layout's width
                mTitleLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 4f));

                // Make the LandmarkWebviewFragment take 2/3's of the layout's width
                mDataLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 8f));
            }
        }
    }

    // Called when the user selects an item in the TitleFragment
    @Override
    public void onListSelection(int index) {

        // If the LandmarkWebviewFragment has not been added, add it now
        if (!mDataFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the LandmarkWebviewFragment to the layout
            fragmentTransaction.replace(R.id.data_fragment_container,
                    mDataFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }
        mDataFragment.showDataAtIndex(index);

    }
}
