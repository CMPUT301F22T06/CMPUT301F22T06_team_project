package com.git_er_done.cmput301f22t06_team_project.fragments;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.git_er_done.cmput301f22t06_team_project.R;
import com.git_er_done.cmput301f22t06_team_project.adapters.IngredientsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.adapters.MealsRecyclerViewAdapter;
import com.git_er_done.cmput301f22t06_team_project.callbacks.SwipeToDeleteIngredientCallback;
import com.git_er_done.cmput301f22t06_team_project.dbHelpers.IngredientDBHelper;
import com.git_er_done.cmput301f22t06_team_project.models.ingredient.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.core.WeekDay;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;
import com.kizitonwose.calendar.view.ViewContainer;
import com.kizitonwose.calendar.view.WeekCalendarView;
import com.kizitonwose.calendar.view.WeekDayBinder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/**
 * Fragment that holds the meal planner calendar and associated scrollable list of each meal planned for the selected date.
 */
public class MealPlannerFragment extends Fragment {

    CalendarView calendarView;
    private LocalDate selectedDate;

    RecyclerView rvMeals;
    MealsRecyclerViewAdapter rvAdapter;

    MonthDayBinder<?> monthDayBinder;
    MonthHeaderFooterBinder<?> monthHeaderFooterBinder;

    WeekCalendarView weekCalendarView;
    WeekDayBinder weekDayBinder;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");

    /**
     * Required empty public constructor
     */
    public MealPlannerFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Meal Planner");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_meal_planner, container, false);

        calendarView = root.findViewById(R.id.cv_meal_planner_calendar);
        weekCalendarView = root.findViewById(R.id.cv_meal_planner_week_calendar);
        rvMeals = root.findViewById(R.id.rv_meal_plans);

        ArrayList<Ingredient> getIngredientsFromStorage = IngredientDBHelper.getIngredientsFromStorage();


        setupMonthDayBinder();
        setupMonthHeaderBinder();

        setupWeekDayBinder();


        YearMonth currentMonth = YearMonth.now();
        DayOfWeek dayOfWeek = firstDayOfWeekFromLocale();
        YearMonth startDate = currentMonth.minusMonths(24);
        YearMonth endDate = currentMonth.plusMonths(24);

        calendarView.setup(startDate, endDate, dayOfWeek);
        calendarView.scrollToDate(LocalDate.now());
        calendarView.setDayBinder(monthDayBinder);
        calendarView.setMonthHeaderBinder(monthHeaderFooterBinder);

        LocalDate startDateForWeek = LocalDate.now().minus(14, ChronoUnit.DAYS);
        LocalDate endDateForWeek = LocalDate.now().plus(14, ChronoUnit.DAYS);

        weekCalendarView.setup(startDateForWeek, endDateForWeek, dayOfWeek);
        weekCalendarView.scrollToDate(LocalDate.now());
        weekCalendarView.setDayBinder(weekDayBinder);
        weekCalendarView.setVisibility(View.INVISIBLE);

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView(){
        rvAdapter = new MealsRecyclerViewAdapter();
        rvMeals.setAdapter(rvAdapter);
        rvMeals.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteIngredientCallback(rvAdapter));
//        itemTouchHelper.attachToRecyclerView(rvAdapter);
    }

    void setupMonthDayBinder(){
        monthDayBinder = new MonthDayBinder<DayViewContainer>() {
            /**
             * Binding is done in increasing chronological order from first day of month to last
             * @param container Contains the viewHolder for this binder. Call .getView() to attach view layouts accordingly
             * @param calendarDay represents the calendar day that is currently being binded as library specific CalendarDay object
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay calendarDay) {
                TextView text = container.getView().findViewById(R.id.tv_calendar_day_text);
                LinearLayout dateBackground = container.getView().findViewById(R.id.layout_around_calendar_date);
                int dayInt = calendarDay.getDate().getDayOfMonth();
                text.setText(Integer.toString(dayInt));
                container.calendarDay = calendarDay;

                if(container.calendarDay.getPosition() == DayPosition.MonthDate){
                    text.setVisibility(View.VISIBLE);

                    if(container.calendarDay.getDate() == selectedDate){
                        text.setTextColor(Color.WHITE);
//                        textView.setBackgroundResource(R.drawable.selection_background);
                        dateBackground.setBackgroundColor(Color.BLUE);
                    }
                    else{
                        text.setBackground(null);
                        if(container.calendarDay.getDate().equals(LocalDate.now())){
                            text.setTextColor(Color.BLACK);
                            dateBackground.setBackgroundColor(Color.WHITE);
                        }
                        else{
                            text.setTextColor(Color.BLACK);
                            dateBackground.setBackgroundColor(Color.RED);
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
    }



    /**
     * Binds the calendar_day_title_container layout to the calendar object.
     * Gives the calendar a header with the associated month and year.
     */
    void setupMonthHeaderBinder(){
        monthHeaderFooterBinder = new MonthHeaderFooterBinder<MonthViewContainer>() {
            @Override
            public void bind(@NonNull MonthViewContainer container, CalendarMonth calendarMonth) {
                TextView text = container.getView().findViewById(R.id.tv_calendar_day_title);
                int year = calendarMonth.getYearMonth().getYear();
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
    }

    /**
     * ViewContainer for the 'all days of the month' layout of the calendarView
     * Setups on click listener for each individual day
     */
    public class DayViewContainer extends ViewContainer
    {
        TextView dateText;
        CalendarDay calendarDay;

        WeekDay calendarWeekDay;

        public DayViewContainer(@NonNull View view) {
            super(view);
            dateText = view.findViewById(R.id.tv_calendar_day_text);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String dateNumString = dateText.getText().toString();
                    Log.d("Calendar", "Calendar date: " + dateNumString + "  clicked");

                    LocalDate currentSelection = selectedDate;

                    //If the date belongs to the current calendar month
                    if(calendarDay.getPosition() == DayPosition.MonthDate){

                        //if we select an already selected date
                        if(currentSelection == calendarDay.getDate()){
                            selectedDate = null;
                            //reload date so the daybinder is called and we can remove the selection background
                            calendarView.notifyDateChanged(currentSelection);
                        }
                        //if we select a date that is not already selected
                        else{
                            //Only allow user to select a date if there is not one selected yet.
                            //This ensures only one date is selected at a time.
                            if(selectedDate == null) {
                                selectedDate = calendarDay.getDate();
                                calendarView.notifyDateChanged(calendarDay.getDate());

                                if (currentSelection != null) {
                                    // need to also reload the previously selected date to remove selection background
                                    calendarView.notifyDateChanged(calendarDay.getDate());
                                }
                            }
                            else{
                                Toast.makeText(getContext(), "Date: " + selectedDate.toString() + " is already selected!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        }
    }

    public class MonthViewContainer extends ViewContainer {
        public MonthViewContainer(@NonNull View view) {
            super(view);
        }
    }

    public void setupWeekDayBinder(){

        weekDayBinder = new WeekDayBinder<DayViewContainer>() {
            @Override
            public void bind(@NonNull DayViewContainer container, WeekDay weekDay) {
                container.calendarWeekDay = weekDay;
                TextView dateText = container.getView().findViewById(R.id.cv_meal_planner_week_calendar_date_text);
                TextView dayText = container.getView().findViewById(R.id.cv_meal_planner_week_calendar_day_text);

                //Set date text
                dateText.setText(dateFormatter.format(weekDay.getDate()));

                //Set day text
            }

            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }
        };
    }

}