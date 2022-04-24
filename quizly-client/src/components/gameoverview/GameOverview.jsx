import React from "react";
import "./GameOverview.css";
import { Tab, Tabs, TabList, TabPanel } from "react-tabs";
import Players from "../players/Players";
import QuizSettings from "../QuizSettings";
import Categories from "../Categories";
import { useContext } from "react";
import { QuizContext } from "../../context/QuizContext";

export default function GameOverview() {
  const { tabIndex, setTabIndex } = useContext(QuizContext);

  return (
    <Tabs selectedIndex={tabIndex} onSelect={(index) => setTabIndex(index)}>
      <TabList>
        <Tab>1. Spelers</Tab>
        <Tab>2. Categorieen</Tab>
        <Tab>3. Instellingen</Tab>
      </TabList>

      <TabPanel>
        <Players />
      </TabPanel>
      <TabPanel>
        <Categories />
      </TabPanel>
      <TabPanel>
        <QuizSettings />
      </TabPanel>
    </Tabs>
  );
}
