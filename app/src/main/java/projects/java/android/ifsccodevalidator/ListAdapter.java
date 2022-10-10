package projects.java.android.ifsccodevalidator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<List> {
    public ListAdapter(@NonNull Context context, @NonNull ArrayList<List> lists) {
        super(context, 0, lists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        List list = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview, parent, false);
        }
        ((AppCompatTextView) convertView.findViewById(R.id.textView)).setText(list.getText());
        ((AppCompatTextView) convertView.findViewById(R.id.resultTv)).setText(list.getResultText());
        return convertView;
    }
}
