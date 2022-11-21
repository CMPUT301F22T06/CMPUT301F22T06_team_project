package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import com.git_er_done.cmput301f22t06_team_project.DayViewContainer;
import com.git_er_done.cmput301f22t06_team_project.R;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;

public class MealPlannerFragment extends Fragment {

    CalendarView calendarView;

    private LocalDate selectedDate;

    public MealPlannerFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Meal Planner");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_meal_planner, container, false);
        calendarView = root.findViewById(R.id.calendarView);


        MonthDayBinder<?> monthDayBinder = new MonthDayBinder<DayViewContainer>() {
            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay calendarDay) {
                TextView text = container.getView().findViewById(R.id.tv_calendar_day_text);
                Integer dayInt = calendarDay.getDate().getDayOfMonth();
                text.setText(dayInt.toString());

                container.day = calendarDay;

                if(container.day.getPosition() == DayPosition.MonthDate){
                    text.setVisibility(View.VISIBLE);

                    if(container.day.getDate() == selectedDate){
                        text.setTextColor(Color.BLUE);
//                        textView.setBackgroundResource(R.drawable.selection_background);
                    }
                    else{
                        text.setBackground(null);
                        if(container.day.getDate().equals(LocalDate.now())){
                            text.setTextColor(Color.WHITE);
                        }
                        else{
                            text.setTextColor(Color.BLACK);
                        }
                    }
                }
                else{
                    text.setVisibility(View.INVISIBLE);
                }
            }

            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }
        };

        MonthHeaderFooterBinder<?> monthHeaderFooterBinder = new MonthHeaderFooterBinder<MonthViewContainer>() {
            @Override
            public void bind(@NonNull MonthViewContainer container, CalendarMonth calendarMonth) {
                TextView text = container.getView().findViewById(R.id.tv_calendar_day_title);
                Integer year = calendarMonth.getYearMonth().getYear();
                String month = calendarMonth.getYearMonth().getMonth().toString();

                String calendarTitle = month + ",  " + year;
                text.setText(calendarTitle);
            }

            @NonNull
            @Override
            public MonthViewContainer create(@NonNull View view) {
                return new MonthViewContainer(view);
            }

        };

        YearMonth currentMonth = YearMonth.now();
        DayOfWeek dayOfWeek = firstDayOfWeekFromLocale();

        YearMonth startDate = currentMonth.minusMonths(24);
        YearMonth endDate = currentMonth.plusMonths(24);

        calendarView.setup(startDate, endDate, dayOfWeek);
        calendarView.scrollToDate(LocalDate.now());
        calendarView.setDayBinder(monthDayBinder);
        calendarView.setMonthHeaderBinder(monthHeaderFooterBinder);

        return root;
    }

    public class DayViewContainer extends ViewContainer
    {

        TextView dateText;
        CalendarDay day;

        public DayViewContainer(@NonNull View view) {
            super(view);
            dateText = view.findViewById(R.id.tv_calendar_day_text);
//        yearText = view.findViewById(R.id.t
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Calendar", "Calendar date: " + dateText.getText().toString() + "  clicked");

                    LocalDate currentSelection = selectedDate;

                    //If the date belongs to the current calendar month
                    if(day.getPosition() == DayPosition.MonthDate){

                        //if we select an already selected date
                        if(currentSelection == day.getDate()){
                            selectedDate = null;
                            //reload date so the daybinder is called and we can remove the selection background
                            calendarView.notifyDateChanged(currentSelection);
                        }
                        //if we select a date that is not already selected
                        else{
                            selectedDate = day.getDate();
                            calendarView.notifyDateChanged(day.getDate());

                            if(currentSelection != null){
                                // need to also reload the previously selected date to remove selection background
                                calendarView.notifyDateChanged(day.getDate());
                            }
                        }
                    }
                }
            });
        }
    }

    public class MonthViewContainer extends ViewContainer {
//        TextView monthText;

        public MonthViewContainer(@NonNull View view) {
            super(view);
        }
    }

}