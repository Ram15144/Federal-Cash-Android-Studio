package course.examples.Services.KeyClient;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by ram_n on 12/5/2017.
 */

public class TitleFragment extends android.app.ListFragment {
    public static final String TAG="TitleFragment";
    private ListSelectionListener mListener=null;
    private int mCurrIdx=-1;

    //Callback interface used to notify the MainActivity when user selects an item
    public interface ListSelectionListener
    {
        public void onListSelection(int index);
    }

    //Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id)
    {
        if(mCurrIdx != pos)
        {
            mCurrIdx=pos;
            // Inform the MainActivity that the item in position pos has been selected
            mListener.onListSelection(pos);
        }
        // Indicates the selected item has been checked
        getListView().setItemChecked(pos, true);

        // Inform the MainActivity that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try
        {
            mListener=(ListSelectionListener) context;
        }
        catch(Exception excepion)
        {
            throw new ClassCastException(context.toString()
            + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.list_fragment_layout, DisplayData.mTitleArray));


        // If an item has been selected, set its checked state
        if (-1 != mCurrIdx) {
            getListView().setItemChecked(mCurrIdx, true);

            // UB:  10-6-2017 Added this call to handle configuration changes
            // that broke in API 25
            mListener.onListSelection(mCurrIdx);
        }

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
